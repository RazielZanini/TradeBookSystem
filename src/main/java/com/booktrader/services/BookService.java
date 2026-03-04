package com.booktrader.services;

import com.booktrader.domain.book.Book;
import com.booktrader.domain.user.User;
import com.booktrader.dtos.SuccessMessageDTO;
import com.booktrader.dtos.request.RequestBookDTO;
import com.booktrader.dtos.response.ResponseBookDTO;
import com.booktrader.dtos.response.ResponseReviewDTO;
import com.booktrader.repositories.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository repository;

    private final UserService userService;

    public BookService(BookRepository bookRepository, UserService userService){
        this.userService = userService;
        this.repository = bookRepository;
    }

    public Book findBookById(Long id) throws IllegalArgumentException{
        return this.repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Livro não encontrado"));
    }

    public List<ResponseBookDTO> listBooks(){

        return this.repository.findAll()
                .stream()
                .map(ResponseBookDTO::from)
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

        return ResponseBookDTO.from(newBook);
    }

    public ResponseBookDTO updateBook(Long bookId, RequestBookDTO book) {

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

            return ResponseBookDTO.from(foundBook);
    }

    public SuccessMessageDTO deleteBook(Long id){
        this.repository.deleteById(id);

        return new SuccessMessageDTO("Livro excluido com sucesso!", "200");
    }

    public List<ResponseReviewDTO> getBookReviews(Long id) {
        Book book = findBookById(id);

        return book.getReviews()
                .stream()
                .map(ResponseReviewDTO::from)
                .collect(Collectors.toList());
    }
}
