package com.example.thuongmai.repository;

import com.example.thuongmai.model.notification.Notification;
import com.example.thuongmai.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface INotificationRepository extends JpaRepository<Notification, Long> {
    Iterable<Notification> findAllByUser(User user);
}
