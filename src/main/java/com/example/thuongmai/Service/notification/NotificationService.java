package com.example.thuongmai.Service.notification;

import com.example.thuongmai.model.notification.Notification;
import com.example.thuongmai.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NotificationService implements INotificationService {

    @Autowired
    private INotificationService notificationService;

    @Override
    public Iterable<Notification> findAll() {
        return notificationService.findAll();
    }

    @Override
    public Optional<Notification> findById(Long id) {
        return notificationService.findById(id);
    }

    @Override
    public Notification save(Notification notification) {
        return notificationService.save(notification);
    }

    @Override
    public void deleteById(Long id) {
        notificationService.deleteById(id);
    }

    @Override
    public Iterable<Notification> findAllByUser(User user) {
        return notificationService.findAllByUser(user);
    }
}
