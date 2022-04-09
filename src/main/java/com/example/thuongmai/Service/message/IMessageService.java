package com.example.thuongmai.Service.message;

import com.example.thuongmai.Service.IGeneralService;
import com.example.thuongmai.model.message.Message;
import com.example.thuongmai.model.roomchat.RoomChat;

public interface IMessageService extends IGeneralService<Message> {
    Iterable<Message> findAllByRoomChat(RoomChat roomChat);
}
