package com.booktrader.services;

import com.booktrader.domain.book.Book;
import com.booktrader.domain.review.Review;
import com.booktrader.domain.user.User;
import com.booktrader.dtos.request.UserRequestDTO;
import com.booktrader.dtos.response.UserBasicDTO;
import com.booktrader.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.Assert.notNull;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private BookService bookService;

    public void saveUser(User user){
        this.repository.save(user);
    }

    public List<UserBasicDTO> getAllUsers(){
        return this.repository.findAll()
                .stream()
                .map(user -> new UserBasicDTO(
                        user.getId(),
                        user.getName(),
                        user.getEmail()
                ))
                .collect(Collectors.toList());
    }

    public User findUserById(Long id) throws IllegalArgumentException{

        notNull(id, "O parametro id é obrigatório");

        return this.repository.findUserById(id)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao encontrar usuário"));
    }

    public List<Book> getBooksByUser(Long id){
        User user = findUserById(id);

        return user.getBooks();
    }

    public List<Review> getReviewsByUser(Long id){
            User user = findUserById(id);

            return user.getReviews();
    }

    public User updateUser(Long id, UserRequestDTO user){
        User updateUser = findUserById(id);

        updateUser.setName(user.name());
        updateUser.setPassword(user.password());
        updateUser.setEmail(user.email());

        return repository.save(updateUser);
    }
}
