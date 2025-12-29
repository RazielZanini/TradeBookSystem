package com.booktrader.services;

import com.booktrader.domain.book.Book;
import com.booktrader.domain.notification.Notification;
import com.booktrader.domain.trade.Trade;
import com.booktrader.domain.trade.TradeStatus;
import com.booktrader.domain.user.User;
import com.booktrader.dtos.request.RequestTradeDTO;
import com.booktrader.dtos.response.ResponseBookDTO;
import com.booktrader.dtos.response.ResponseTradeDTO;
import com.booktrader.dtos.response.UserBasicDTO;
import com.booktrader.infra.exceptions.ControllerExceptionHandler;
import com.booktrader.repositories.TradeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class TradeService {

    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notificationService;

    public Trade findTradeById(Long id){
        return this.tradeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Troca não encontrada: " + id));
    }

    public List<Trade> findAllTrades(){
        return this.tradeRepository.findAll();
    }


    @Transactional
    public ResponseTradeDTO createTrade(RequestTradeDTO trade){

        User sender = this.userService.findUserById(trade.sender());
        User receiver = this.userService.findUserById(trade.receiver());
        Book senderBook = this.bookService.findBookById(trade.senderBook());
        Book receiverBook = this.bookService.findBookById(trade.receiverBook());

        validateTrade(sender, receiver, senderBook, receiverBook);

        Trade newTrade = tradeRepository.save(
                Trade.builder()
                        .sender(sender)
                        .receiver(receiver)
                        .senderBook(senderBook)
                        .receiverBook(receiverBook)
                        .status(TradeStatus.PENDING)
                        .build()
        );

        notificationService.notifyTradeToUser(
                sender,
                receiver,
                senderBook,
                receiverBook,
                newTrade.getId()
        );

        return ResponseTradeDTO.from(newTrade);
    }

    @Transactional
    public void respondToTrade(Long tradeId, boolean accept, Long loggedUserId) throws Exception {

        Trade trade = this.findTradeById(tradeId);

        if(!trade.getStatus().equals(TradeStatus.PENDING)){
            throw new IllegalStateException("A troca ja foi processada!");
        }

        if(!checkUserIsReceiver(trade.getReceiver().getId(), loggedUserId)){
            throw new SecurityException("Este usuário não está autorizado a responder esta troca.");
        }

        if(accept){
            trade.setStatus(TradeStatus.ACCEPTED);
            processAcceptTrade(trade);
        } else {
            trade.setStatus(TradeStatus.REJECTED);
        }

        tradeRepository.save(trade);
    }

    public boolean checkUserIsReceiver(Long receiverId, Long userId){
        return receiverId.equals(userId);
    }

    private void processAcceptTrade(Trade trade){
        User sender = trade.getSender();
        User receiver = trade.getReceiver();
        Book senderBook = trade.getSenderBook();
        Book receiverBook = trade.getReceiverBook();

        // Troca de livros entre usuários
        sender.getBooks().remove(senderBook);
        receiver.getBooks().remove(receiverBook);
        sender.getBooks().add(receiverBook);
        receiver.getBooks().add(senderBook);

        // Atualiza donos
        senderBook.setOwner(receiver);
        receiverBook.setOwner(sender);

        // Persiste mudanças
        userService.saveUser(sender);
        userService.saveUser(receiver);
        bookService.saveBook(senderBook);
        bookService.saveBook(receiverBook);

        trade.setStatus(TradeStatus.COMPLETED);
    }


    private void validateTrade(User sender, User receiver, Book senderBook, Book receiverBook) {
        if (Objects.equals(sender.getId(), receiver.getId())) {
            throw new RuntimeException("Não é possível uma troca entre o mesmo usuário.");
        }
        if (!Objects.equals(senderBook.getOwner().getId(), sender.getId())) {
            throw new RuntimeException("O usuário remetente não possui este livro.");
        }
        if (!Objects.equals(receiverBook.getOwner().getId(), receiver.getId())) {
            throw new RuntimeException("O usuário destinatário não possui este livro.");
        }
    }
}
