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

    public Trade findTradeById(Long id) throws Exception{
        return this.tradeRepository.findTradeById(id)
                .orElseThrow(() -> new Exception("Troca não encontrada"));
    }

    public List<Trade> findAllTrades(){
        return this.tradeRepository.findAll();
    }


    @Transactional
    public ResponseTradeDTO createTrade(RequestTradeDTO trade){

        User sender = this.userService.findUserById(trade.sender());
        User receiver = this.userService.findUserById(trade.receiver());
        Book receiverBook = this.bookService.findBookById(trade.receiverBook());
        Book senderBook = this.bookService.findBookById(trade.senderBook());

        validateTrade(sender, receiver, senderBook, receiverBook);

        // Cria a troca com status "PENDING"
        Trade newTrade = Trade.builder()
                .senderBook(senderBook)
                .receiverBook(receiverBook)
                .receiver(receiver)
                .sender(sender)
                .status(TradeStatus.PENDING)
                .build();

        this.tradeRepository.save(newTrade);

        notificationService.NotificateTradeToUser(sender, receiver, receiverBook, senderBook, newTrade.getId());

        return new ResponseTradeDTO(
                new UserBasicDTO(sender.getId(), sender.getName(), sender.getEmail()),
                new UserBasicDTO(receiver.getId(), receiver.getName(), receiver.getEmail()),
                new ResponseBookDTO(senderBook.getId(), senderBook.getTitle(), senderBook.getAuthor(), senderBook.getImage()),
                new ResponseBookDTO(receiverBook.getId(), receiverBook.getTitle(), receiverBook.getAuthor(), receiverBook.getImage())
        );
    }

    @Transactional
    public void respondToTrade(Long tradeId, boolean accept) throws Exception {

        Trade trade = this.findTradeById(tradeId);

        if (!trade.getStatus().equals(TradeStatus.PENDING)) {
            throw new IllegalStateException("A troca já foi processada.");
        }

        if (accept) {
            User sender = trade.getSender();
            User receiver = trade.getReceiver();
            Book senderBook = trade.getSenderBook();
            Book receiverBook = trade.getReceiverBook();

            // Troca de livros entre usuários
            sender.getBooks().remove(senderBook);
            receiver.getBooks().remove(receiverBook);
            sender.getBooks().add(receiverBook);
            receiver.getBooks().add(senderBook);

            // Atualiza os donos dos livros
            senderBook.setOwner(receiver);
            receiverBook.setOwner(sender);

            // Persiste as alterações
            userService.saveUser(sender);
            userService.saveUser(receiver);
            bookService.saveBook(senderBook);
            bookService.saveBook(receiverBook);

            trade.setStatus(TradeStatus.COMPLETED);
            // notificateUser(...) — opcional
        } else {
            trade.setStatus(TradeStatus.REJECTED);
            // notificateUser(...) — opcional
        }

        tradeRepository.save(trade);
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
