package com.booktrader.controllers;

import com.booktrader.domain.notification.Notification;
import com.booktrader.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @GetMapping("/{userId}")
    public List<Notification> getUnreadNotifications(@PathVariable Long userId) throws Exception {
        return this.notificationService.getUnreadNotifications(userId);
    }
}