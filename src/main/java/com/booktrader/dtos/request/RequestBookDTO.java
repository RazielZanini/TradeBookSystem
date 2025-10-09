package com.booktrader.dtos.request;

import com.booktrader.domain.book.ConservStatus;

public record RequestBookDTO(
        String title,
        String author,
        int edition,
        ConservStatus conservStatus,
        Long owner,
        String image) {
}
