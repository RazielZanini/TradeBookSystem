package com.booktrader.services;

import com.booktrader.domain.book.Book;
import com.booktrader.domain.review.Review;
import com.booktrader.domain.user.User;
import com.booktrader.dtos.UserDTO;
import com.booktrader.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<User> getAllUsers(){
        return this.repository.findAll();
    }

    public User findUserById(Long id) throws IllegalArgumentException{

        notNull(id, "O parametro id é obrigatório");

        return this.repository.findUserById(id)
                .orElseThrow(() -> new IllegalArgumentException("Erro ao encontrar usuário"));
    }

    public List<Book> getBooksByUser(Long id) throws Exception{
        User user = this.repository.findUserById(id)
                .orElseThrow(() -> new Exception("Erro ao buscar livros de usuário"));

        return user.getBooks();
    }

    public List<Review> getReviewsByUser(Long id) throws Exception{
        User user = this.repository.findUserById(id)
                .orElseThrow(() -> new Exception("Erro ao buscar livros de usuário"));

        return user.getReviews();
    }

    public User updateUser(Long id, UserDTO user) throws Exception{
        User updateUser = findUserById(id);

        updateUser.setName(user.name());
        updateUser.setPassword(user.password());
        updateUser.setEmail(user.email());

        return repository.save(updateUser);
    }
}
