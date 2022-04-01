package com.example.thuongmai.controller;

import com.example.thuongmai.Service.itemcart.IItemCartService;
import com.example.thuongmai.model.cart.ItemCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/item-carts")
public class ItemCartController {
    @Autowired private IItemCartService iItemCartService;
    @GetMapping
    public ResponseEntity<Iterable<ItemCart>> findAllItemCart(){
        return new ResponseEntity<>(iItemCartService.findAll(), HttpStatus.OK);
    }
}
