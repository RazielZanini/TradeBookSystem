package com.booktrader.controllers;

import com.booktrader.domain.notification.Notification;
import com.booktrader.dtos.response.ResponseNotificationDTO;
import com.booktrader.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<ResponseNotificationDTO>> getUnreadNotifications(@PathVariable Long userId) throws Exception {
        List<ResponseNotificationDTO> notifications = this.notificationService.getUnreadNotifications(userId);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }
}