package com.example.thuongmai.model.dto.chat;

import com.example.thuongmai.model.roomchat.RoomChat;
import com.example.thuongmai.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageForm {
    private String content;
    private User user;
    private RoomChat roomChat;
}
