package com.booktrader.dtos;

import com.booktrader.domain.book.Book;
import com.booktrader.domain.user.User;

import java.time.LocalDateTime;

public record ReviewDTO(String review, Long writer, Long reviewedBook, Integer criticNote) {
}
