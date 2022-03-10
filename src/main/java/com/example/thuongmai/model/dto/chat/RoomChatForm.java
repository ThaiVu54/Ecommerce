package com.example.thuongmai.model.dto.chat;

import com.example.thuongmai.model.shop.Shop;
import com.example.thuongmai.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomChatForm {
    private String name;
    private Shop shop;
    private User user;
}
