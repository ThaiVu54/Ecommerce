package com.example.thuongmai.controller;

import com.example.thuongmai.Service.roomchat.IRoomChatService;
import com.example.thuongmai.model.dto.chat.RoomChatForm;
import com.example.thuongmai.model.roomchat.RoomChat;
import com.example.thuongmai.model.shop.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/rooms")
public class RoomChatController {
    @Autowired
    private IRoomChatService roomChatService;

    @GetMapping("/{shopId}/{userId}")
    public ResponseEntity<RoomChat> findByShopIdAndUserId(@PathVariable Long shopId, @PathVariable Long userId) {
        Optional<RoomChat> roomChatOptional = roomChatService.findByShopIdAndUserId(shopId, userId);
        if (!roomChatOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(roomChatOptional.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RoomChat> save(@RequestBody RoomChatForm roomChatForm) {
        RoomChat roomChat = new RoomChat();
        Optional<RoomChat> currentRoom = roomChatService.findByShopIdAndUserId(roomChatForm.getShop().getId(), roomChatForm.getUser().getId());
        if (currentRoom.isPresent()) {
            return new ResponseEntity<>(currentRoom.get(), HttpStatus.OK);
        }
        roomChat.setMessages(new ArrayList<>());
        roomChat.setName(roomChatForm.getName());
        roomChat.setShop(roomChatForm.getShop());
        roomChat.setUser(roomChatForm.getUser());
        return new ResponseEntity<>(roomChatService.save(roomChat),HttpStatus.ACCEPTED);
    }

    @PutMapping("/shop")
    public ResponseEntity<Iterable<RoomChat>> findAllByShop(@RequestBody Shop shop){
        return new ResponseEntity<>(roomChatService.findAllByShop(shop),HttpStatus.OK);
    }
}
