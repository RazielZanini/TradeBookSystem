package com.booktrader.controllers;

import com.booktrader.domain.book.Book;
import com.booktrader.domain.review.Review;
import com.booktrader.dtos.BookDTO;
import com.booktrader.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody BookDTO book) throws Exception{
        Book newBook = this.bookService.createBook(book);
        return new ResponseEntity<>(newBook, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks(){
        List<Book> books = this.bookService.findAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<Book> editBook(@RequestBody BookDTO data,@PathVariable("bookId") Long bookId) throws Exception {
        Book updatedBook = this.bookService.updateBook(bookId, data);
        return new ResponseEntity<>(updatedBook, HttpStatus.CREATED);
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Book> deleteBook(@PathVariable("bookId") Long bookId) throws Exception{
        Book deletedBook = this.bookService.findBookById(bookId);
        this.bookService.deleteBook(bookId);
        return new ResponseEntity<>(deletedBook, HttpStatus.OK);
    }

    @GetMapping("/{bookId}/reviews")
    public ResponseEntity<List<Review>> getBookReviews(@PathVariable Long bookId) throws Exception{
        List<Review> reviews = this.bookService.getBookReviews(bookId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }
}
