package com.booktrader.dtos;

import com.booktrader.domain.user.UserRole;

public record UserDTO(String name, String password, UserRole role, String email) {
}
