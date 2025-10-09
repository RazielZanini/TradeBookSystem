package com.booktrader.domain.review;

import com.booktrader.domain.book.Book;
import com.booktrader.domain.user.User;
import com.booktrader.dtos.request.RequestReviewDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "reviews")
@Table(name = "reviews")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="writer_id")
    @JsonIgnoreProperties({"books", "reviews", "password", "authorities", "username"})
    private User writer;

    @ManyToOne
    @JoinColumn(name="reviewedBook_id")
    @JsonIgnoreProperties({"reviews", "owner"})
    private Book reviewedBook;

    private String review;

    private int criticNote;

    private LocalDateTime createdAt;

    public Review(RequestReviewDTO data){
        this.review = data.review();
    }

    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();
    }
}
