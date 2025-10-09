package com.booktrader.services;

import com.booktrader.domain.book.Book;
import com.booktrader.domain.notification.Notification;
import com.booktrader.domain.user.User;
import com.booktrader.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;

    public void saveNotification(Notification notification) { this.notificationRepository.save(notification); }

    public List<Notification> getUnreadNotifications(Long userId) throws Exception {
        List<Notification> notifications = this.notificationRepository.findByUserIdAndReadFalse(userId);

        if (notifications.isEmpty()) {
            throw new Exception("Nenhuma notificação encontrada.");
        }

        notifications.forEach(n -> n.setRead(true));

        this.notificationRepository.saveAll(notifications);

        return notifications;
    }

    public void NotificateTradeToUser(User sender, User receiver, Book userBook, Book tradeBook, Long tradeId) {
        String message = sender.getName() + " propôs uma troca do seu livro '" + tradeBook.getTitle() +
                "' por '" + userBook.getTitle() + "'. Você aceita? (ID da troca: " + tradeId + ")";

        Notification notification = new Notification(message, userBook, tradeBook, receiver, tradeId);
        this.saveNotification(notification);
    }

}