package com.example.thuongmai.repository;

import com.example.thuongmai.model.message.Message;
import com.example.thuongmai.model.roomchat.RoomChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMessageRepository extends JpaRepository<Message, Long> {
    Iterable<Message> findAllByRoomChat(RoomChat roomChat);
}
