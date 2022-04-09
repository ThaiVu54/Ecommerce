package com.example.thuongmai.Service.message;

import com.example.thuongmai.model.message.Message;
import com.example.thuongmai.model.roomchat.RoomChat;
import com.example.thuongmai.repository.IMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MessageService implements IMessageService {
    @Autowired
    private IMessageRepository messageRepository;

    @Override
    public Iterable<Message> findAll() {
        return messageRepository.findAll();
    }

    @Override
    public Optional<Message> findById(Long id) {
        return messageRepository.findById(id);
    }

    @Override
    public Message save(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public void deleteById(Long id) {
        messageRepository.deleteById(id);
    }

    @Override
    public Iterable<Message> findAllByRoomChat(RoomChat roomChat) {
        return messageRepository.findAllByRoomChat(roomChat);
    }
}
