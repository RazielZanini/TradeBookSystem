package com.booktrader.services;

import com.booktrader.domain.book.Book;
import com.booktrader.domain.review.Review;
import com.booktrader.domain.user.User;
import com.booktrader.dtos.request.RequestBookDTO;
import com.booktrader.dtos.response.ResponseBookDTO;
import com.booktrader.dtos.response.ResponseReviewDTO;
import com.booktrader.dtos.response.UserBasicDTO;
import com.booktrader.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository repository;
    @Autowired
    @Lazy
    private UserService userService;

    public Book findBookById(Long id) throws IllegalArgumentException{
        return this.repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Livro não encontrado"));
    }

    public List<ResponseBookDTO> listBooks(){

        return this.repository.findAll()
                .stream()
                .map(book -> new ResponseBookDTO(
                        book.getId(),
                        book.getTitle(),
                        book.getAuthor(),
                        book.getImage()
                ))
                .collect(Collectors.toList());
    }

    public void saveBook(Book book){
        this.repository.save(book);
    }

    public ResponseBookDTO createBook(RequestBookDTO data){
        Book newBook = new Book(data);
        User owner = this.userService.findUserById(data.owner());

        newBook.setOwner(owner);
        owner.getBooks().add(newBook);

        this.userService.saveUser(owner);
        this.saveBook(newBook);

        return new ResponseBookDTO(newBook.getId(), newBook.getTitle(), newBook.getAuthor(), newBook.getImage());
    }

    public Book updateBook(Long bookId, RequestBookDTO book) {
        Book foundBook = findBookById(bookId);
        User owner = foundBook.getOwner();

        owner.getBooks().remove(foundBook);
        foundBook.setTitle(book.title());
        foundBook.setAuthor(book.author());
        foundBook.setEdition(book.edition());
        foundBook.setConservStatus(book.conservStatus());

        owner.getBooks().add(foundBook);
        this.repository.save(foundBook);
        this.userService.saveUser(owner);

        return foundBook;
    }

    public void deleteBook(Long id){
        this.repository.deleteById(id);
    }

    public List<ResponseReviewDTO> getBookReviews(Long id) {
        Book book = findBookById(id);

        return book.getReviews()
                .stream()
                .map(review -> new ResponseReviewDTO(
                        review.getId(),
                        new UserBasicDTO(review.getWriter().getId(), review.getWriter().getName(), review.getWriter().getEmail()),
                        new ResponseBookDTO(review.getReviewedBook().getId(), review.getReviewedBook().getTitle(), review.getReviewedBook().getAuthor(), review.getReviewedBook().getImage()),
                        review.getReview(),
                        review.getCriticNote(),
                        review.getCreatedAt()
                )).collect(Collectors.toList());
    }

    public UserBasicDTO getUserInfo(Long user_id){
        User user = userService.findUserById(user_id);
        return new UserBasicDTO(user.getId(), user.getName(), user.getEmail());
    }
}
