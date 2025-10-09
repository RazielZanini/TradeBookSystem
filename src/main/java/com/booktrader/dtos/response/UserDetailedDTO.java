package com.booktrader.dtos.response;

import java.util.Collections;
import java.util.List;

public record UserDetailedDTO(
        Long id,
        String name,
        String email,
        List<ResponseBookDTO> books,
        List<ResponseReviewDTO> reviews)
{

    @Override
    public List<ResponseBookDTO> books(){
        return books != null ? books : Collections.emptyList();
    }

    @Override
    public List<ResponseReviewDTO> reviews(){
        return reviews != null ? reviews : Collections.emptyList();
    }

}
