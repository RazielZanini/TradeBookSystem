package com.booktrader.services;

import com.booktrader.domain.book.Book;
import com.booktrader.domain.book.ConservStatus;
import com.booktrader.domain.user.User;
import com.booktrader.domain.user.UserRole;
import com.booktrader.dtos.UserDTO;
import com.booktrader.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository repository;

    User user;
    Book book;
    UserDTO userDTO;

    @BeforeEach
    public void setUp(){
        user = new User("Raziel", "123123", UserRole.USER, "raziel@gmail.com");
        user.setId(1L);
        book = new Book("teste", "autor teste", "", user, 1, ConservStatus.NEW);
        userDTO = new UserDTO("novo nome","novasenha", UserRole.USER, "novoemail");
    }

    @Test
    void deveBuscarUsuarioPeloIdComSucesso() throws IllegalArgumentException {
        assertNotNull(user.getId());
        when(repository.findUserById(user.getId())).thenReturn(Optional.of(user));

        User returnedUser = userService.findUserById(user.getId());

        assertEquals(user, returnedUser);
        verify(repository).findUserById(user.getId());
        verifyNoMoreInteractions(repository);
        System.out.println("Sucesso ao retornar usuário: " + returnedUser.getId() + returnedUser.getEmail());
    }

    @Test
    void naoDeveChamarRepositoryCasoParametroIdNulo(){
        final IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            userService.findUserById(null);
        });

        assertThat(e, notNullValue());
        assertThat(e.getMessage(), is("O parametro id é obrigatório"));
        verifyNoInteractions(repository);

        System.out.println("Sucesso ao não interagir com repositório caso parametro null");
    }

    @Test
    void deveRetornarUsuarioAtualizadoComSucesso() throws Exception {

        assertNotNull(user.getId()); // Garantir que o id não é nulo

        when(repository.findUserById(user.getId())).thenReturn(Optional.of(user)); // quando o metodo for utilizado retorna um user
        when(repository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0)); // quando repositorio.save for chamado retorna o argumento inicial

        User updatedUser = userService.updateUser(user.getId(), userDTO); // Recebe usuário atualizado

        assertEquals(user.getId(), updatedUser.getId()); // Compara o usuário é o mesmo
        assertEquals(userDTO.name(), updatedUser.getName()); // Compara se o nome alterado é o mesmo do solicitado
        assertEquals(userDTO.password(), updatedUser.getPassword());
        assertEquals(userDTO.email(), updatedUser.getEmail());

        verify(repository).findUserById(user.getId());
        verify(repository).save(any(User.class));
        verifyNoMoreInteractions(repository);

        System.out.println("Sucesso ao atualizar usuário!");
    }
}
