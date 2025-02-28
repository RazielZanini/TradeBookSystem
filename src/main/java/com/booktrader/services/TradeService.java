package com.booktrader.services;

import com.booktrader.domain.book.Book;
import com.booktrader.domain.trade.Trade;
import com.booktrader.domain.user.User;
import com.booktrader.dtos.TradeDTO;
import com.booktrader.repositories.TradeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeService {

    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    public Trade findTradeById(Long id) throws Exception{
        return this.tradeRepository.findTradeById(id)
                .orElseThrow(() -> new Exception("Troca não encontrada"));
    }

    public List<Trade> findAllTrades(){
        return this.tradeRepository.findAll();
    }


    @Transactional
    public Trade createTrade(TradeDTO trade) throws Exception {

        User sender = this.userService.findUserById(trade.sender());
        User receiver = this.userService.findUserById(trade.receiver());
        Book book = this.bookService.findBookById(trade.tradedBook());

        if (sender.equals(receiver)) {
            throw new Exception("Não é possível uma troca entre o mesmo usuário.");
        }

        if (!book.getOwner().equals(sender)) {
            throw new Exception("O usuário remetente não possui este livro.");
        }

        book.setOwner(receiver);
        sender.getBooks().remove(book);
        receiver.getBooks().add(book);

        this.userService.saveUser(sender);
        this.userService.saveUser(receiver);

        Trade newTrade = new Trade();
        newTrade.setTradedBook(book);
        newTrade.setReceiver(receiver);
        newTrade.setSender(sender);
        this.tradeRepository.save(newTrade);

        return newTrade;
    }



}
