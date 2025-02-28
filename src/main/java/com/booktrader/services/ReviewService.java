package com.booktrader.services;

import com.booktrader.domain.book.Book;
import com.booktrader.domain.review.Review;
import com.booktrader.domain.user.User;
import com.booktrader.dtos.ReviewDTO;
import com.booktrader.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository repository;
    @Autowired
    private BookService bookService;
    @Autowired
    private  UserService userService;

    public void saveReview(Review review){
        this.repository.save(review);
    }

    public Review getReviewById(Long id) throws Exception{
        return this.repository.findReviewById(id)
                .orElseThrow(() -> new Exception("Review não encontrada!"));
    }

    public Review createReview(ReviewDTO data) throws Exception{
        User writer = this.userService.findUserById(data.writer());
        Book reviewedBook = this.bookService.findBookById(data.reviewedBook());

        Review newReview = new Review();

        newReview.setWriter(writer);
        newReview.setReview(data.review());
        newReview.setTimeStamp(LocalDateTime.now());
        newReview.setReviewedBook(reviewedBook);

        this.saveReview(newReview);
        writer.getReviews().add(newReview);
        reviewedBook.getReviews().add(newReview);
        this.bookService.saveBook(reviewedBook);
        this.userService.saveUser(writer);

        return newReview;
    }

    public Review editReview(Long id, ReviewDTO review) throws Exception{
        Review foundReview = this.repository.findReviewById(id)
                .orElseThrow(() -> new Exception("Erro ao encontrar review"));

        foundReview.setReview(review.review());
        foundReview.setTimeStamp(review.timeStamp());

        this.saveReview(foundReview);

        return foundReview;
    }

    public Review deleteReview(Long id) throws Exception{
        Review deletedReview = this.repository.findReviewById(id)
                .orElseThrow(() -> new Exception("Erro ao deletar review: Review não encontrada"));
        this.repository.deleteById(id);
        return  deletedReview;
    }
}
