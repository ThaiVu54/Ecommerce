package com.example.thuongmai.controller;

import com.example.thuongmai.Service.myshop.IMyShopService;
import com.example.thuongmai.Service.shop.IShopService;
import com.example.thuongmai.Service.user.IUserService;
import com.example.thuongmai.enums.EnumFollowShop;
import com.example.thuongmai.enums.EnumRoles;
import com.example.thuongmai.enums.EnumShop;
import com.example.thuongmai.enums.EnumShopType;
import com.example.thuongmai.model.dto.shop.ShopCreate;
import com.example.thuongmai.model.role.Role;
import com.example.thuongmai.model.shop.MyShop;
import com.example.thuongmai.model.shop.Shop;
import com.example.thuongmai.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/shops")
public class ShopController {
    @Autowired
    private IShopService shopService;
    @Autowired
    private IMyShopService myShopService;
    @Autowired
    private IUserService userService;

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
        return new ResponseEntity<>(currentMyShop.get(), HttpStatus.OK);
    }

    //tạo shop
    @PostMapping()
    public ResponseEntity<?> createShop(@RequestBody ShopCreate shopCreate) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        String username = userDetails.getUsername();
        Optional<User> userOptional = userService.findByUsername(username);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        int check = 0;
        for (Role role : userOptional.get().getRoles()) {
            if (role.getName() == EnumRoles.ROLE_PROVIDER) {
                check = 1;

            }
            if (role.getName() == EnumRoles.ROLE_VIP) {
                check = 2;
                break;
            }
        }
        if (check == 2 || check == 0) {
            if (check == 0) {
                userOptional.get().getRoles().add(new Role(3L, EnumRoles.ROLE_PROVIDER));
            }
            if (check == 2 && userOptional.get().getShops().size() >= 5) {
                return new ResponseEntity<>("Đã đạt giới hạn tạo shop",HttpStatus.NO_CONTENT);
            }
            Shop shop = new Shop();
            shop.setName(shopCreate.getName());
            shop.setDescription(shopCreate.getDescription());
            shop.setViewShop(0L);
            shop.setCountFollow(0L);
            shop.setStatus(EnumShop.ACTIVITY);
            shop.setStartOpen(LocalDate.now());
            shop.setAvatar(shopCreate.getAvatar());
            shop.setTurnover(0L);
            shop.setType(EnumShopType.NORMAL);
            shop.setRoomChats(new ArrayList<>());
            MyShop myShop = new MyShop();
            myShop.setShop(shopService.save(shop));
            myShop.setUser(userOptional.get());
            myShop.setFollowOrOwner(EnumFollowShop.OWNER);
            return new ResponseEntity<>(myShopService.save(myShop), HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("Hãy trở thành VIP để tạo thêm shop mới", HttpStatus.NO_CONTENT);
    }
}
