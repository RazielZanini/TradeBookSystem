package com.booktrader.services;

import com.booktrader.domain.book.Book;
import com.booktrader.domain.review.Review;
import com.booktrader.domain.user.User;
import com.booktrader.dtos.ReviewDTO;
import com.booktrader.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        newReview.setReviewedBook(reviewedBook);
        if(data.criticNote() < 0 || data.criticNote() > 5){
            throw new IllegalArgumentException ("A nota deve ser um valor entre 0 e 5");
        }
        newReview.setCriticNote(data.criticNote());

        this.saveReview(newReview);
        writer.getReviews().add(newReview);
        reviewedBook.getReviews().add(newReview);
        this.bookService.saveBook(reviewedBook);
        this.userService.saveUser(writer);

        return newReview;
    }

    public Review editReview(Long id, ReviewDTO review) throws Exception{
        Review foundReview = getReviewById(id);

        foundReview.setReview(review.review());
        foundReview.setCriticNote(review.criticNote());

        return this.repository.save(foundReview);
    }

    public Review deleteReview(Long id) throws Exception{
        Review deletedReview = this.repository.findReviewById(id)
                .orElseThrow(() -> new Exception("Erro ao deletar review: Review não encontrada"));
        this.repository.deleteById(id);

        return  deletedReview;
    }
}
