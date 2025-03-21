package com.booktrader.services;

import com.booktrader.domain.book.Book;
import com.booktrader.domain.notification.Notification;
import com.booktrader.domain.user.User;
import com.booktrader.dtos.NotificationDTO;
import com.booktrader.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
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
        List<Notification> notifications = this.notificationRepository.findByUserIdAndReadFalse(userId)
                .orElse(Collections.emptyList());

        if (notifications.isEmpty()) {
            throw new Exception("Nenhuma notificação encontrada.");
        }

        notifications.forEach(n -> n.setRead(true));

        this.notificationRepository.saveAll(notifications);

        return notifications;
    }


    public Notification createNotification(NotificationDTO data) throws Exception{

        User user = this.userService.findUserById(data.userId());
        Book userBook = this.bookService.findBookById(data.userBookId());
        Book tradeBook = this.bookService.findBookById(data.tradeBookId());

        Notification newNotification = new Notification(data.message(), userBook, tradeBook, user, data.tradeId());

        this.saveNotification(newNotification);

        return newNotification;
    }
}
