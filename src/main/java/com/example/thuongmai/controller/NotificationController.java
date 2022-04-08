package com.example.thuongmai.controller;

import com.example.thuongmai.Service.notification.INotificationService;
import com.example.thuongmai.Service.user.IUserService;
import com.example.thuongmai.model.notification.Notification;
import com.example.thuongmai.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/notifications")
public class NotificationController {
    @Autowired
    private IUserService userService;
    @Autowired
    private INotificationService notificationService;
    @PutMapping()
    public ResponseEntity<Iterable<Notification>> findAllByUser(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        String username = userDetails.getUsername();
        Optional<User> currentUser = userService.findByUsername(username);
        if (!currentUser.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(notificationService.findAllByUser(currentUser.get()),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Notification> deleteById(@PathVariable Long id){
        notificationService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
