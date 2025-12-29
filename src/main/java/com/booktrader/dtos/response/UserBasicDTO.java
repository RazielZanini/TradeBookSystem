package com.booktrader.dtos.response;

import com.booktrader.domain.user.User;

public record UserBasicDTO(Long id, String name, String email) {

    public static UserBasicDTO from (User user){
        return new UserBasicDTO(user.getId(), user.getName(), user.getEmail());
    }
}
