package com.example.thuongmai.controller;

import com.example.thuongmai.Service.message.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("")
@RequestMapping("/messages")
public class MessageController {
    @Autowired private IMessageService messageService;

}
