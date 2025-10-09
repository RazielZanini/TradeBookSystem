package com.booktrader.controllers;

import com.booktrader.domain.review.Review;
import com.booktrader.dtos.request.RequestReviewDTO;
import com.booktrader.dtos.response.ResponseReviewDTO;
import com.booktrader.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReview(@PathVariable("reviewId") Long reviewId) throws Exception{
        Review review = this.reviewService.getReviewById(reviewId);
        return new ResponseEntity<>(review, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseReviewDTO> createReview(@RequestBody RequestReviewDTO data) throws Exception{
        ResponseReviewDTO newReview = this.reviewService.createReview(data);
        return new ResponseEntity<>(newReview, HttpStatus.CREATED);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ResponseReviewDTO> updateReview(@PathVariable("reviewId")Long reviewId, RequestReviewDTO data) throws Exception{
        ResponseReviewDTO updatedReview = this.reviewService.editReview(reviewId, data);
        return new ResponseEntity<>(updatedReview, HttpStatus.OK);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Review> deleteReview(@PathVariable("reviewId")Long reviewId) throws Exception{
        Review deletedReview = this.reviewService.deleteReview(reviewId);
        return new ResponseEntity<>(deletedReview, HttpStatus.OK);
    }
}
