package com.example.thuongmai.controller;

import com.example.thuongmai.Service.myshop.IMyShopService;
import com.example.thuongmai.Service.shop.IShopService;
import com.example.thuongmai.model.shop.MyShop;
import com.example.thuongmai.model.shop.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/shops")
public class ShopController {
    @Autowired
    private IShopService shopService;
    @Autowired
    private IMyShopService myShopService;

    @GetMapping
    public ResponseEntity<Iterable<Shop>> getAll() {
        return new ResponseEntity<>(shopService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MyShop> findById(@PathVariable("id") Long id) {
        Optional<MyShop> currentMyShop = myShopService.findById(id);
        if (!currentMyShop.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        currentMyShop.get().getShop().setViewShop(currentMyShop.get().getShop().getViewShop() + 1);
        shopService.save(currentMyShop.get().getShop());
        return new ResponseEntity<>(currentMyShop.get(),HttpStatus.OK);
    }
}
