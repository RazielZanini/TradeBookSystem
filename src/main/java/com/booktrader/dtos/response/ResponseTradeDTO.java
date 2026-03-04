package com.booktrader.dtos.response;

import com.booktrader.domain.trade.Trade;

public record ResponseTradeDTO(
        Long id,
        UserBasicDTO sender,
        UserBasicDTO receiver,
        ResponseBookDTO senderBook,
        ResponseBookDTO receiverBook) {

    public static ResponseTradeDTO from(Trade trade) {
        return new ResponseTradeDTO(
                trade.getId(),
                UserBasicDTO.from(trade.getSender()),
                UserBasicDTO.from(trade.getReceiver()),
                ResponseBookDTO.from(trade.getSenderBook()),
                ResponseBookDTO.from(trade.getReceiverBook()));
    }
}
