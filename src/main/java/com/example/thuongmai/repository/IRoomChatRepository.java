package com.example.thuongmai.repository;

import com.example.thuongmai.model.roomchat.RoomChat;
import com.example.thuongmai.model.shop.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoomChatRepository extends JpaRepository<RoomChat, Long> {
    Iterable<RoomChat> findAllByShop(Shop shop);
    @Query(value = "select * from room_chat where shop_id = ? and user_id = ?",nativeQuery = true)
    Optional<RoomChat> findByShopIdAndUserId(Long shopId, Long userId);
}
