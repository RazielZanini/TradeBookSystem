package com.booktrader.dtos.request;

public record RequestReviewDTO(
        String review,
        Long writer,
        Long reviewedBook,
        Integer criticNote) {
}
