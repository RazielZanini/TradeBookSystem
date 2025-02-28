package com.booktrader.dtos;

import com.booktrader.domain.book.ConservStatus;

public record BookDTO(String title, String author, int edition, ConservStatus conservStatus, Long owner) {
}
