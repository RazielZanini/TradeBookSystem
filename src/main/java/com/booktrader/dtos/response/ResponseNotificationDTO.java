package com.booktrader.dtos.response;

import java.time.LocalDateTime;

public record ResponseNotificationDTO(
        Long id,
        String message,
        String bookName,
        String userName,
        LocalDateTime craeatedAt
) {
}
