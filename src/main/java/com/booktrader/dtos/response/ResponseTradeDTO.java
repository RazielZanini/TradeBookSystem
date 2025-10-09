package com.booktrader.dtos.response;

public record ResponseTradeDTO(
        UserBasicDTO sender,
        UserBasicDTO receiver,
        ResponseBookDTO senderBook,
        ResponseBookDTO receiverBook) {
}
