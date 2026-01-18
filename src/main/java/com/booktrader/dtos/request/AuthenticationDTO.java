package com.booktrader.dtos.request;

import jakarta.validation.constraints.NotEmpty;

public record AuthenticationDTO(@NotEmpty(message = "Email é obrigatório") String email,
                                @NotEmpty(message = "Senha é obrigatória") String password) {
}
