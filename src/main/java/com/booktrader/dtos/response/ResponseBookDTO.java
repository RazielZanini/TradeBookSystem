package com.booktrader.dtos.response;

import com.booktrader.domain.book.Book;

public record ResponseBookDTO(
        Long id,
        String title,
        String author,
        String image
        ) {

        public static ResponseBookDTO from(Book book){
                return new ResponseBookDTO(
                        book.getId(),
                        book.getTitle(),
                        book.getAuthor(),
                        book.getImage()
                );
        }
}