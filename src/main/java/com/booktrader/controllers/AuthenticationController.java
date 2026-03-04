package com.booktrader.controllers;

import com.booktrader.domain.user.User;
import com.booktrader.dtos.request.AuthenticationDTO;
import com.booktrader.dtos.response.LoginResponseDTO;
import com.booktrader.dtos.request.UserRequestDTO;
import com.booktrader.infra.security.TokenService;
import com.booktrader.repositories.UserRepository;
import jakarta.annotation.Nullable;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.Response;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final TokenService tokenService;

    private final BCryptPasswordEncoder passwordEncoder;

    public AuthenticationController(
            AuthenticationManager _authenticationManager,
            UserRepository _userRepository,
            TokenService _tokenService,
            BCryptPasswordEncoder _passwordEncoder
    ){
        this.authenticationManager = _authenticationManager;
        this.userRepository = _userRepository;
        this.tokenService = _tokenService;
        this.passwordEncoder = _passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Validated AuthenticationDTO data){
        try{
            var userNamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
            var auth = this.authenticationManager.authenticate(userNamePassword);

            var token = tokenService.generateToken((User) auth.getPrincipal());

            return ResponseEntity.ok(new LoginResponseDTO(token));
        } catch (EntityNotFoundException error){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Validated UserRequestDTO data){

        String encryptedPassword = passwordEncoder.encode(data.password());
        User newUser = User.builder().name(data.name()).email(data.email()).password(encryptedPassword).role(data.role()).build();

        this.userRepository.save(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário criado com sucesso!");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(){
        return ResponseEntity.ok("Logout realizado no cliente");
    }
}
