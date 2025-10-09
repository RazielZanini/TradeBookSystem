package com.booktrader.domain.book;

import com.booktrader.domain.review.Review;
import com.booktrader.domain.user.User;
import com.booktrader.dtos.request.RequestBookDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name="books")
@Table(name="books")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private String image;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @JsonIgnoreProperties({"books", "reviews", "password", "authorities", "username", "role", "accountNonExpired", "accountNonLocked", "credentialsNonExpired", "enabled"})
    private User owner;
    private int edition;

    @Enumerated(EnumType.STRING)
    private ConservStatus conservStatus;

    @OneToMany(mappedBy = "reviewedBook")
    private List<Review> reviews = new ArrayList<>();

    public Book(RequestBookDTO data){
        this.title = data.title();
        this.author = data.author();
        this.conservStatus = data.conservStatus();
        this.edition = data.edition();
        this.image = data.image();
    }

    public Book(String title, String author, String image, User owner, int edition, ConservStatus conservStatus){
        this.title = title;
        this.author = author;
        this.image = image;
        this.owner = owner;
        this.edition = edition;
        this.conservStatus = conservStatus;
    }
}
