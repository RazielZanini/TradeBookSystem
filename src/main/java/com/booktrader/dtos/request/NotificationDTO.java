package com.booktrader.dtos.request;

public record NotificationDTO(String message, Long userBookId, Long tradeBookId, Long userId, Long tradeId) {

}
