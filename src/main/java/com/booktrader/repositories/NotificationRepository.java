package com.booktrader.repositories;

import com.booktrader.domain.notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Optional<List<Notification>> findByUserIdAndReadFalse(Long userId);

}
