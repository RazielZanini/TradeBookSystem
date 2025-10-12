package com.booktrader.dtos.request;

public record NotificateTradeDTO(String message, Long userBookId, Long tradeBookId, Long userId, Long tradeId) {

}
