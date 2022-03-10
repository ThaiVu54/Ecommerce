package com.example.thuongmai.controller;

import com.example.thuongmai.Service.cart.ICartService;
import com.example.thuongmai.Service.user.IUserService;
import com.example.thuongmai.model.cart.Cart;
import com.example.thuongmai.model.dto.user.UserChangeAvatar;
import com.example.thuongmai.model.dto.user.UserChangePassword;
import com.example.thuongmai.model.dto.user.UserEdit;
import com.example.thuongmai.model.dto.user.UserRecharge;
import com.example.thuongmai.model.user.User;
import com.example.thuongmai.repository.ICartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {
    @Autowired
    IUserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    ICartService cartService;

    @GetMapping
    public ResponseEntity<Page<User>> fillAll(@PageableDefault(value = 4) Pageable pageable) {
        return new ResponseEntity<>(userService.findAllPage(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        Optional<User> currentUser = userService.findById(id);
        if (!currentUser.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(currentUser.get(), HttpStatus.OK);
    }

    @GetMapping("/edit")
    public ResponseEntity<User> editById(@RequestBody UserEdit userEdit) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Optional<User> userOptional = userService.findByUsername(username);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        User user = userOptional.get();
        user.setName(userEdit.getName());
        user.setAvatar(userEdit.getAvatar());
        user.setPhone(userEdit.getPhone());
        user.setEmail(userEdit.getEmail());
        user.setAddress(userEdit.getAddress());
        Optional<Cart> cartOptional = cartService.findByUser(user);
        if (!cartOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        cartOptional.get().setName("Giỏ hàng của " + user.getName());
        cartService.save(cartOptional.get());
        return new ResponseEntity<>(userService.save(user), HttpStatus.ACCEPTED);
    }

    @PutMapping("/change-avatar")
    public ResponseEntity<User> changeAvatar(@RequestBody UserChangeAvatar userChangeAvatar) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Optional<User> currentUser = userService.findByUsername(username);
        if (!currentUser.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        currentUser.get().setAvatar(userChangeAvatar.getAvatar());
        return new ResponseEntity<>(userService.save(currentUser.get()), HttpStatus.OK);
    }

    @PutMapping("/change-password")
    public ResponseEntity<User> changePassword(@RequestBody UserChangePassword userChangePassword) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, userChangePassword.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Optional<User> currentUser = userService.findByUsername(username);
        currentUser.get().setPassword(passwordEncoder.encode(userChangePassword.getPassword()));
        return new ResponseEntity<>(userService.save(currentUser.get()), HttpStatus.OK);
    }

    @PutMapping("/recharge")
    public ResponseEntity<?> recharge(@RequestBody UserRecharge userRecharge) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Optional<User> currentUser = userService.findByUsername(username);
        if (!currentUser.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        currentUser.get().setWallet(currentUser.get().getWallet() + userRecharge.getMoney());
        return new ResponseEntity<>(userService.save(currentUser.get()), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        userService.deleteById(id);
        Optional<User> currentUser = userService.findById(id);
        if (!currentUser.isPresent()) {
            return new ResponseEntity<>("Delete success", HttpStatus.OK);
        }
        return new ResponseEntity<>("Delete fails", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/info")
    public ResponseEntity<User> showInfo(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Optional<User> currentUser = userService.findByUsername(username);
        if (!currentUser.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        User user = currentUser.get();
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @GetMapping("/user/{myShopId}")
    public ResponseEntity<?> showMyShop(@PathVariable Long myShopId){
        Optional<User> currentUser = userService.findUserByMyShopId(myShopId);
        if (!currentUser.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(currentUser.get(),HttpStatus.ACCEPTED);
    }
    @GetMapping("/user-buyed/{productId}")
    public ResponseEntity<Iterable<User>> findAllUserBuyer(@PathVariable Long productId){
        return new ResponseEntity<>(userService.findAllByUserBuyProduct(productId), HttpStatus.OK);
    }
    @GetMapping("/user-shop/{shopId}")
    public ResponseEntity<User> findByShops(@PathVariable Long shopId){
        Optional<User> currentUser = userService.findByShopId(shopId);
        if (!currentUser.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(currentUser.get(), HttpStatus.OK);
    }
}
