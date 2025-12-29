package com.booktrader.controllers;

import com.booktrader.domain.trade.Trade;
import com.booktrader.domain.user.User;
import com.booktrader.dtos.request.RequestTradeDTO;
import com.booktrader.dtos.response.ResponseTradeDTO;
import com.booktrader.dtos.response.TradeResponseMessageDTO;
import com.booktrader.services.TradeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trades")
public class TradeController {

    @Autowired
    private TradeService tradeService;

    @GetMapping
    public ResponseEntity<List<Trade>> getAllTrades() {
        List<Trade> trades = this.tradeService.findAllTrades();
        return new ResponseEntity<>(trades, HttpStatus.OK);
    }

    @GetMapping("/{tradeId}")
    public ResponseEntity<Trade> getTradeById(@PathVariable("tradeId") Long tradeId){
        Trade trade = this.tradeService.findTradeById(tradeId);
        return new ResponseEntity<>(trade, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseTradeDTO> createTrade(@RequestBody RequestTradeDTO data){
        ResponseTradeDTO newTrade = this.tradeService.createTrade(data);
        return new ResponseEntity<>(newTrade, HttpStatus.CREATED);
    }

    @PostMapping("/{tradeId}/respond")
    public ResponseEntity<?> respondTrade(@PathVariable Long tradeId, @RequestParam boolean accept, @AuthenticationPrincipal User userDetails) {
        try{
            tradeService.respondToTrade(tradeId, accept, userDetails.getId());

            return ResponseEntity.ok(
                    new TradeResponseMessageDTO(
                            tradeId,
                            accept ? "COMPLETED" : "REJECTED",
                            accept ? "Troca aceita com sucesso!" : "Troca rejeitada com sucesso!"
                    )
            );
        } catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new TradeResponseMessageDTO(tradeId, "ERRO", e.getMessage()));
        } catch (SecurityException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new TradeResponseMessageDTO(tradeId, "ERRO", e.getMessage()));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new TradeResponseMessageDTO(tradeId, "ERRO", e.getMessage()));
        }
    }
}
