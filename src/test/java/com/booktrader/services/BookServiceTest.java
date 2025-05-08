package com.booktrader.services;

import com.booktrader.domain.book.Book;
import com.booktrader.domain.book.ConservStatus;
import com.booktrader.domain.review.Review;
import com.booktrader.domain.user.User;
import com.booktrader.domain.user.UserRole;
import com.booktrader.dtos.BookDTO;
import com.booktrader.repositories.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @InjectMocks
    BookService bookService;

    @Mock
    BookRepository bookRepository;

    @Mock
    UserService userService;

    User testUser;
    Book book;

    @BeforeEach
    public void setUp(){
        testUser = new User("teste", "teste", UserRole.USER, "teste@mail.com");
        testUser.setId(1L);
        book = new Book("tituloTeste", "AutorTeste", "", testUser, 1, ConservStatus.NEW);
        book.setId(1L);
        testUser.getBooks().add(book);
    }

    @Test
    @DisplayName("Deve Buscar Livro pelo id")
    void deveBuscarLivroPeloIdComSucesso() throws IllegalArgumentException {
        Assertions.assertNotNull(book.getId());
        when(bookRepository.findBookById(book.getId())).thenReturn(Optional.of(book));

        Book returnedBook = bookService.findBookById(book.getId());

        Assertions.assertEquals(book, returnedBook);
        Mockito.verify(bookRepository).findBookById(book.getId());
        Mockito.verifyNoMoreInteractions(bookRepository);
        System.out.println("Sucesso ao retornar Livro: " + returnedBook.getId() + " " + returnedBook.getTitle());
    }

    @Test
    @DisplayName("Deve retornar lista de livros")
    void deveRetornarUmaListaDeLivros(){
        List<Book> books = List.of(book);
        Pageable pageable = PageRequest.of(0,5);
        Page<Book> bookPage = new PageImpl<>(books, pageable, 0);

        when(bookRepository.findAll(pageable)).thenReturn(bookPage);

        Page<Book> result = bookService.listBooks(pageable);

        Assertions.assertNotNull(result);
        assertEquals(1, result.getContent().size());
        Mockito.verify(bookRepository).findAll(pageable);
        Mockito.verifyNoMoreInteractions(bookRepository);
        System.out.println("Encontrada lista de livros");
    }

    @Test
    @DisplayName("Deve retornar livro atualizado com sucesso")
    void deveRetornarLivroComInformacoesAlteradas() throws Exception {

        BookDTO bookDTO = new BookDTO("TituloTrocado", "AutorTrocado", 2, ConservStatus.NEW, testUser.getId(), "");

        assertNotNull(book.getId());

        when(bookRepository.findBookById(book.getId())).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Book updatedBook = bookService.updateBook(book.getId(), bookDTO);

        assertEquals(book.getId(), updatedBook.getId());
        assertEquals(bookDTO.title(), updatedBook.getTitle());
        assertEquals(bookDTO.author(), updatedBook.getAuthor());
        assertEquals(bookDTO.conservStatus(), updatedBook.getConservStatus());
        assertEquals(bookDTO.edition(), updatedBook.getEdition());
        assertEquals(bookDTO.owner(), testUser.getId());
        assertEquals(bookDTO.image(), updatedBook.getImage());

        verify(bookRepository).findBookById(book.getId());
        verify(bookRepository).save(any(Book.class));
        verifyNoMoreInteractions(bookRepository);

        System.out.println("Sucesso ao atualizar livro!");
    }

    @Test
    @DisplayName("Deve retornar lista de reviews atreladas ao livro")
    void deveRetornarListaDeReviews() throws Exception {
        Review review = new Review(1L, testUser, book, "Muito Bom!", 5, LocalDateTime.now());
        book.setReviews(List.of(review));

        when(bookRepository.findBookById(book.getId())).thenReturn(Optional.of(book));

        List<Review> result = bookService.getBookReviews(book.getId());

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Muito Bom!", result.get(0).getReview());

        verify(bookRepository).findBookById(book.getId());
        verifyNoMoreInteractions(bookRepository);

        System.out.println("Sucesso!");
    }
}