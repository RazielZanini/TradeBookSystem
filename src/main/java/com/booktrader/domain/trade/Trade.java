package com.booktrader.domain.trade;

import com.booktrader.domain.book.Book;
import com.booktrader.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name="trades")
@Table(name="trades")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
@Builder
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    @JsonIgnoreProperties({"books", "reviews", "password", "authorities"})
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    @JsonIgnoreProperties({"books", "reviews", "password", "authorities"})
    private User receiver;

    @ManyToOne
    @JoinColumn(name = "senderBook_id")
    @JsonIgnoreProperties({"owner", "reviews"})
    private Book senderBook;

    @ManyToOne
    @JoinColumn(name = "receiverBook_id")
    @JsonIgnoreProperties({"owner", "reviews"})
    private Book receiverBook;

    @Enumerated(EnumType.STRING)
    private TradeStatus status = TradeStatus.PENDING;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();
    }
}
