package com.booktrader.dtos.response;

import com.booktrader.domain.review.Review;

import java.time.LocalDateTime;

public record ResponseReviewDTO(
        Long id,
        UserBasicDTO writer,
        ResponseBookDTO reviewedBook,
        String review,
        Integer criticNote,
        LocalDateTime createdAt
) {
    public static ResponseReviewDTO from(Review review) {
        return new ResponseReviewDTO(
                review.getId(),
                UserBasicDTO.from(review.getWriter()),
                ResponseBookDTO.from(review.getReviewedBook()),
                review.getReview(),
                review.getCriticNote(),
                review.getCreatedAt()
                );
    }
}
