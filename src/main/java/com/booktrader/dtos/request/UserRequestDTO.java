package com.booktrader.dtos.request;

import com.booktrader.domain.user.UserRole;

public record UserRequestDTO(String name, String password, UserRole role, String email) {

}
