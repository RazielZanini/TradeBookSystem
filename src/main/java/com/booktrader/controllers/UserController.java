package com.booktrader.controllers;

import com.booktrader.domain.book.Book;
import com.booktrader.domain.review.Review;
import com.booktrader.domain.user.User;
import com.booktrader.dtos.UserDTO;
import com.booktrader.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDTO user){
        User newUser = userService.createUser(user);
        return new ResponseEntity<>(newUser,HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = this.userService.getAllUsers();
        return  new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> editUser(@RequestBody UserDTO data, @PathVariable("userId") Long userId) throws Exception {
        User updatedUser = userService.updateUser(userId, data);
        return new ResponseEntity<>(updatedUser, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}/books")
    public ResponseEntity<List<Book>> getUserBooks(@PathVariable("userId") Long userId) throws Exception {
        List<Book> books = userService.getBooksByUser(userId);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/{userId}/reviews")
    public ResponseEntity<List<Review>> getUserReviews(@PathVariable("userId")Long userId) throws Exception {
        List<Review> reviews = this.userService.getReviewsByUser(userId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }


}
