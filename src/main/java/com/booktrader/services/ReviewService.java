package com.booktrader.services;

import com.booktrader.domain.book.Book;
import com.booktrader.domain.review.Review;
import com.booktrader.domain.user.User;
import com.booktrader.dtos.ExceptionDTO;
import com.booktrader.dtos.SuccessMessageDTO;
import com.booktrader.dtos.request.RequestReviewDTO;
import com.booktrader.dtos.response.ResponseBookDTO;
import com.booktrader.dtos.response.ResponseReviewDTO;
import com.booktrader.dtos.response.UserBasicDTO;
import com.booktrader.repositories.ReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public Review findReviewById(Long id){
        return this.repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public ResponseReviewDTO getReviewById(Long id) throws Exception{
        Review review = this.repository.findById(id).orElseThrow(EntityNotFoundException::new);

        return new ResponseReviewDTO(
                id,
                new UserBasicDTO(review.getWriter().getId(), review.getWriter().getName(), review.getWriter().getEmail()),
                new ResponseBookDTO(review.getReviewedBook().getId(), review.getReviewedBook().getTitle(), review.getReviewedBook().getAuthor(), review.getReviewedBook().getImage()),
                review.getReview(),
                review.getCriticNote(),
                review.getCreatedAt()
        );
    }

    public List<ResponseReviewDTO> getLatestReviewByUser(Long userId){
        return this.repository.findLatestReviewByUser(userId)
                .stream()
                .map(review -> new ResponseReviewDTO(
                        review.getId(),
                        new UserBasicDTO(review.getWriter().getId(), review.getWriter().getName(), review.getWriter().getEmail()),
                        new ResponseBookDTO(review.getReviewedBook().getId(), review.getReviewedBook().getTitle(), review.getReviewedBook().getAuthor(), review.getReviewedBook().getImage()),
                        review.getReview(),
                        review.getCriticNote(),
                        review.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }

    public ResponseReviewDTO createReview(RequestReviewDTO data) throws Exception{
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

        return new ResponseReviewDTO(
                newReview.getId(),
                new UserBasicDTO(writer.getId(), writer.getName(), writer.getEmail()),
                new ResponseBookDTO(reviewedBook.getId(), reviewedBook.getTitle(), reviewedBook.getAuthor(), reviewedBook.getImage()),
                newReview.getReview(),
                newReview.getCriticNote(),
                newReview.getCreatedAt());
    }

    public ResponseReviewDTO editReview(Long id, RequestReviewDTO review) {
        Review foundReview = this.findReviewById(id);

        foundReview.setReview(review.review());
        foundReview.setCriticNote(review.criticNote());

        User writer = foundReview.getWriter();
        Book book = foundReview.getReviewedBook();

        this.repository.save(foundReview);

        return new ResponseReviewDTO(
                foundReview.getId(),
                new UserBasicDTO(writer.getId(), writer.getName(), writer.getEmail()),
                new ResponseBookDTO(book.getId(), book.getTitle(), book.getAuthor(), book.getImage()),
                foundReview.getReview(),
                foundReview.getCriticNote(),
                foundReview.getCreatedAt());
    }

    public SuccessMessageDTO deleteReview(Long id) throws Exception{

        if(this.findReviewById(id) == null){
            throw new RuntimeException("Review não encontrada!");
        }

        this.repository.deleteById(id);

        return new SuccessMessageDTO("Review excluida com sucesso!", "200");
    }
}
