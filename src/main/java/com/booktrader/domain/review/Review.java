package com.booktrader.domain.review;

import com.booktrader.domain.book.Book;
import com.booktrader.domain.user.User;
import com.booktrader.dtos.ReviewDTO;
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
    @JsonIgnoreProperties({"books", "reviews"})
    private User writer;

    @ManyToOne
    @JoinColumn(name="reviewedBook_id")
    @JsonIgnoreProperties({"reviews", "owner"})
    private Book reviewedBook;

    private String review;

    private LocalDateTime timeStamp;

    public Review(ReviewDTO data){
        this.review = data.review();
        this.timeStamp = data.timeStamp();
    }
}
