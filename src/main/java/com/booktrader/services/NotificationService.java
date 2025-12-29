package com.booktrader.services;

import com.booktrader.domain.book.Book;
import com.booktrader.domain.notification.Notification;
import com.booktrader.domain.user.User;
import com.booktrader.dtos.response.ResponseNotificationDTO;
import com.booktrader.repositories.NotificationRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public void saveNotification(Notification notification) { this.notificationRepository.save(notification); }

    @Transactional
    public List<ResponseNotificationDTO> getUnreadNotifications(Long userId) throws Exception {
        List<Notification> notifications = this.notificationRepository.findByUserIdAndReadFalse(userId);

        if (notifications.isEmpty()) {
            return List.of();
        }

        notifications.forEach(n -> n.setRead(true));
        this.notificationRepository.saveAll(notifications);

        return notifications.stream()
                .map(n -> new ResponseNotificationDTO(
                        n.getId(),
                        n.getMessage(),
                        n.getTradeBook().getTitle(),
                        n.getUser().getName(),
                        n.getCreatedAt()
                )).collect(Collectors.toList());
    }

    public void notifyTradeToUser(User sender, User receiver, Book userBook, Book tradeBook, Long tradeId) {
        String message = sender.getName() + " propôs uma troca do seu livro '" + tradeBook.getTitle() +
                "' por '" + userBook.getTitle() + "'. Você aceita? (ID da troca: " + tradeId + ")";

        Notification notification = new Notification(message, userBook, tradeBook, receiver, tradeId);
        this.saveNotification(notification);
    }

}