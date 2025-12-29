package com.booktrader.repositories;

import com.booktrader.domain.trade.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeRepository extends JpaRepository<Trade, Long> {

}
