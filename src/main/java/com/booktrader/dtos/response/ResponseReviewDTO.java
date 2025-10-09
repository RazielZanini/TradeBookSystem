package com.booktrader.dtos.response;

import java.time.LocalDateTime;

public record ResponseReviewDTO(
        Long id,
        UserBasicDTO writer,
        ResponseBookDTO reviewedBook,
        String review,
        Integer criticNote,
        LocalDateTime createdAt
) {
}
