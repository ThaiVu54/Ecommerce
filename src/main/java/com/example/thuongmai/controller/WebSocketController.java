package com.example.thuongmai.controller;

import com.example.thuongmai.Service.message.IMessageService;
import com.example.thuongmai.Service.notification.INotificationService;
import com.example.thuongmai.model.dto.chat.MessageForm;
import com.example.thuongmai.model.dto.notification.NotificationForm;
import com.example.thuongmai.model.message.Message;
import com.example.thuongmai.model.notification.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@CrossOrigin("*")
public class WebSocketController {
    @Autowired
    private IMessageService messageService;
    @Autowired
    private INotificationService notificationService;

    @MessageMapping("/messages/{roomId}")
    @SendTo("/topic/messages/{roomId}")
    public Message save(MessageForm messageForm){
        Message message = new Message();
        message.setStatus(false);
        message.setUser(messageForm.getUser());
        message.setDate(LocalDate.now());
        message.setContent(messageForm.getContent());
        message.setRoomChat(messageForm.getRoomChat());
        return messageService.save(message);
    }

    @MessageMapping("/notifications/{username}")
    @SendTo("/topic/notifications/{username}")
    public Notification saveNotification(NotificationForm notificationForm){
        Notification notification = new Notification();
        notification.setUrl(notificationForm.getUrl());
        notification.setContent(notificationForm.getContent());
        notification.setStatus(true);
        notification.setUser(notificationForm.getUser());
        return notificationService.save(notification);
    }
}
