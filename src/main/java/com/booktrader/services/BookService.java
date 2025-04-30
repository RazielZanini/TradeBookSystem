package com.booktrader.services;

import com.booktrader.domain.book.Book;
import com.booktrader.domain.review.Review;
import com.booktrader.domain.user.User;
import com.booktrader.dtos.BookDTO;
import com.booktrader.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository repository;
    @Autowired
    @Lazy
    private UserService userService;

    public Book findBookById(Long id) throws IllegalArgumentException{
        return this.repository.findBookById(id).orElseThrow(() -> new IllegalArgumentException("Livro não encontrado"));
    }

    public List<Book> findAllBooks(){

        return this.repository.findAll();
    }

    public void saveBook(Book book){
        this.repository.save(book);
    }

    public Book createBook(BookDTO data){
        Book newBook = new Book(data);
        User owner = this.userService.findUserById(data.owner());

        newBook.setOwner(owner);
        owner.getBooks().add(newBook);

        this.userService.saveUser(owner);
        this.saveBook(newBook);

        return newBook;
    }

    public Book updateBook(Long bookId, BookDTO book) throws Exception {
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

    public List<Review> getBookReviews(Long id) throws Exception{
        Book book = findBookById(id);

        return book.getReviews();
    }
}
