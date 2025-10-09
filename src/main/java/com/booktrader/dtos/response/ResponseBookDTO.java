package com.booktrader.dtos.response;

import com.booktrader.domain.book.ConservStatus;

public record ResponseBookDTO(
        Long id,
        String title,
        String author,
        String image
        )
{}