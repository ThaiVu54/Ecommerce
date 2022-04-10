package com.example.thuongmai.controller;

import com.example.thuongmai.Service.message.IMessageService;
import com.example.thuongmai.Service.roomchat.IRoomChatService;
import com.example.thuongmai.model.dto.chat.MessageForm;
import com.example.thuongmai.model.message.Message;
import com.example.thuongmai.model.roomchat.RoomChat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("")
@RequestMapping("/messages")
public class MessageController {
    @Autowired
    private IMessageService messageService;
    @Autowired
    private IRoomChatService roomChatService;

    @PutMapping("/room-chat")
    public ResponseEntity<Iterable<Message>> findAllByRoomChat(@RequestBody RoomChat roomChat){
        return new ResponseEntity<>(messageService.findAllByRoomChat(roomChat), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Message> save(@RequestBody MessageForm messageForm){
        Message message = new Message();
        message.setDate(messageForm.getUser().getDate());
        message.setUser(messageForm.getUser());
        message.setContent(messageForm.getContent());
        message.setRoomChat(messageForm.getRoomChat());
        return new ResponseEntity<>(messageService.save(message),HttpStatus.ACCEPTED);
    }

    @PutMapping("/read/{roomchatId}")
    public ResponseEntity<Message> read(@PathVariable Long roomchatId, @RequestBody Message message){
        Optional<RoomChat> roomChat = roomChatService.findById(roomchatId);
        if (!roomChat.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        message.setRoomChat(roomChat.get());
        message.setStatus(true);
        return new ResponseEntity<>(messageService.save(message),HttpStatus.ACCEPTED);
    }
}
