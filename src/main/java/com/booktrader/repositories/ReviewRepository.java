package com.booktrader.repositories;

import com.booktrader.domain.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<Review> findReviewById(Long id);
}
