package com.booktrader.repositories;

import com.booktrader.domain.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM reviews r WHERE r.writer.id = :userId ORDER BY r.createdAt DESC")
    List<Review> findLatestReviewByUser(@Param("userId") Long userId);
}

