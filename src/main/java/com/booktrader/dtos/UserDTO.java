package com.booktrader.dtos;

import com.booktrader.domain.user.User;
import com.booktrader.domain.user.UserRole;

public record UserDTO(String name, String password, UserRole role, String email) {

    public User toUser(UserDTO userDTO){
        return new User(userDTO.name(), userDTO.password(), userDTO.role(), userDTO.email());
    }
}
