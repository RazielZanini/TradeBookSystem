package com.booktrader.domain.book;

import com.booktrader.domain.review.Review;
import com.booktrader.domain.user.User;
import com.booktrader.dtos.BookDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name="books")
@Table(name="books")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @JsonIgnoreProperties({"books", "reviews"})
    private User owner;
    private int edition;

    @Enumerated(EnumType.STRING)
    private ConservStatus conservStatus;

    @OneToMany(mappedBy = "reviewedBook")
    private List<Review> reviews = new ArrayList<>();

    public Book(BookDTO data){
        this.title = data.title();
        this.author = data.author();
        this.conservStatus = data.conservStatus();
        this.edition = data.edition();
    }
}
