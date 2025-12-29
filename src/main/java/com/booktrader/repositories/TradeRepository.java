package com.booktrader.repositories;

import com.booktrader.domain.trade.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TradeRepository extends JpaRepository<Trade, Long> {

}
