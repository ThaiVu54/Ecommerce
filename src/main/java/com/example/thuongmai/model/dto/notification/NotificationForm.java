package com.example.thuongmai.model.dto.notification;

import com.example.thuongmai.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationForm {
    private String content;
    private String url;
    private User user;
}
