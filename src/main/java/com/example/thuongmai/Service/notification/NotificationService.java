package com.example.thuongmai.Service.notification;

import com.example.thuongmai.model.notification.Notification;
import com.example.thuongmai.model.user.User;
import com.example.thuongmai.repository.ICategoryRepository;
import com.example.thuongmai.repository.INotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NotificationService implements INotificationService {

    @Autowired
    private INotificationRepository iNotificationRepository;

    @Override
    public Iterable<Notification> findAll() {
        return iNotificationRepository.findAll();
    }

    @Override
    public Optional<Notification> findById(Long id) {
        return iNotificationRepository.findById(id);
    }

    @Override
    public Notification save(Notification notification) {
        return iNotificationRepository.save(notification);
    }

    @Override
    public void deleteById(Long id) {
        iNotificationRepository.deleteById(id);
    }

    @Override
    public Iterable<Notification> findAllByUser(User user) {
        return iNotificationRepository.findAllByUser(user);
    }
}
