package com.booktrader.services;

import com.booktrader.domain.book.Book;
import com.booktrader.domain.review.Review;
import com.booktrader.domain.user.User;
import com.booktrader.dtos.UserDTO;
import com.booktrader.repositories.BookRepository;
import com.booktrader.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private BookService bookService;

    public void saveUser(User user){
        this.repository.save(user);
    }

    public User createUser(UserDTO data){
        User newUser = new User(data);
        this.saveUser(newUser);
        return newUser;
    }

    public List<User> getAllUsers(){
        return this.repository.findAll();
    }

    public User findUserById(Long id) throws Exception{
        return this.repository.findUserById(id)
                .orElseThrow(() -> new Exception("Erro ao encontrar usuário"));
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
        User updateUser = this.repository.findUserById(id)
                .orElseThrow(() -> new Exception("Erro ao atualizar usuário: Usuário não encontrado"));

        updateUser.setName(user.name());
        updateUser.setPassword(user.password());

        this.saveUser(updateUser);

        return updateUser;
    }
}
