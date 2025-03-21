package com.booktrader.dtos;

public record NotificationDTO(String message, Long userBookId, Long tradeBookId, Long userId, Long tradeId) {

}
