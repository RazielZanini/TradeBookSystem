package com.booktrader.controllers;

import com.booktrader.domain.trade.Trade;
import com.booktrader.dtos.TradeDTO;
import com.booktrader.services.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trades")
public class TradeController {

    @Autowired
    private TradeService tradeService;

    @GetMapping
    public ResponseEntity<List<Trade>> getAllTrades(){
        List<Trade> trades = this.tradeService.findAllTrades();
        return new ResponseEntity<>(trades, HttpStatus.OK);
    }

    @GetMapping("/{tradeId}")
    public ResponseEntity<Trade> getTradeById(@PathVariable("tradeId")Long tradeId) throws Exception {
        Trade trade = this.tradeService.findTradeById(tradeId);
        return new ResponseEntity<>(trade, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Trade> createTrade(@RequestBody TradeDTO data) throws Exception {
        Trade newTrade = this.tradeService.createTrade(data);
        return new ResponseEntity<>(newTrade, HttpStatus.CREATED);
    }
}
