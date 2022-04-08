package com.example.thuongmai.Service.notification;

import com.example.thuongmai.Service.IGeneralService;
import com.example.thuongmai.model.notification.Notification;
import com.example.thuongmai.model.user.User;

public interface INotificationService extends IGeneralService<Notification> {
    Iterable<Notification> findAllByUser(User user);
}
