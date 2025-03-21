package com.booktrader.domain.notification;

import com.booktrader.domain.book.Book;
import com.booktrader.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    @ManyToOne
    @JoinColumn(name = "user_book_id")
    @JsonIgnoreProperties({"owner", "reviews"})
    private Book userBook;

    @ManyToOne
    @JoinColumn(name = "trade_book_id")
    @JsonIgnoreProperties({"owner", "reviews"})
    private Book tradeBook;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"books", "reviews", "password", "authorities"})
    private User user;

    private Long tradeId;

    private LocalDateTime createdAt;

    private boolean read;

    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();
    }

    public Notification(String message, Book userBook, Book tradeBook, User user, Long tradeId){
        this.tradeBook = tradeBook;
        this.message = message;
        this.read = false;
        this.userBook = userBook;
        this.user = user;
        this.tradeId = tradeId;
    }

    public Notification(String message, Book userBook, Book tradeBook, User user){
        this.tradeBook = tradeBook;
        this.message = message;
        this.read = false;
        this.userBook = userBook;
        this.user = user;
        this.tradeId = null;
    }
}
