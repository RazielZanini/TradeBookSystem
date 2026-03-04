package com.booktrader.controllers;

import com.booktrader.domain.book.Book;
import com.booktrader.dtos.SuccessMessageDTO;
import com.booktrader.dtos.request.RequestBookDTO;
import com.booktrader.dtos.response.ResponseBookDTO;
import com.booktrader.dtos.response.ResponseReviewDTO;
import com.booktrader.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<ResponseBookDTO> getBook(@PathVariable Long bookId){
        Book book = bookService.findBookById(bookId);
        ResponseBookDTO response = ResponseBookDTO.from(book);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseBookDTO> createBook(@RequestBody RequestBookDTO book){
        ResponseBookDTO newBook = this.bookService.createBook(book);
        return new ResponseEntity<>(newBook, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ResponseBookDTO>> getAllBooks(){
        List<ResponseBookDTO> books = this.bookService.listBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<ResponseBookDTO> editBook(@RequestBody RequestBookDTO data, @PathVariable("bookId") Long bookId){
        ResponseBookDTO updatedBook = this.bookService.updateBook(bookId, data);
        return new ResponseEntity<>(updatedBook, HttpStatus.CREATED);
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<SuccessMessageDTO> deleteBook(@PathVariable("bookId") Long bookId){
        SuccessMessageDTO message = this.bookService.deleteBook(bookId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/{bookId}/reviews")
    public ResponseEntity<List<ResponseReviewDTO>> getBookReviews(@PathVariable Long bookId){
        List<ResponseReviewDTO> reviews = this.bookService.getBookReviews(bookId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }
}
