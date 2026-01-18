package com.booktrader.dtos.request;

import com.booktrader.domain.user.UserRole;
import jakarta.validation.constraints.NotEmpty;

public record UserRequestDTO(@NotEmpty(message = "Necessário fornecer um nome para o usuário") String name,
                             @NotEmpty(message = "Necessário fornecer uma senha para o usuário") String password,
                             @NotEmpty(message = "Necessário fornecer uma role para o usuário") UserRole role,
                             @NotEmpty(message = "Necessário fornecer um email para o usuário") String email) {

}
