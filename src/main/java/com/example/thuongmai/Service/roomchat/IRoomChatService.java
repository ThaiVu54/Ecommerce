package com.example.thuongmai.Service.roomchat;

import com.example.thuongmai.Service.IGeneralService;
import com.example.thuongmai.model.roomchat.RoomChat;
import com.example.thuongmai.model.shop.Shop;

import java.util.Optional;

public interface IRoomChatService extends IGeneralService<RoomChat> {
    Optional<RoomChat> findByShopIdAndUserId(Long shopId, Long userId);
    Iterable<RoomChat> findAllByShop(Shop shop);
}
