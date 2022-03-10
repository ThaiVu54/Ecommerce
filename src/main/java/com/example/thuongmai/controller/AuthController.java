package com.example.thuongmai.controller;

import com.example.thuongmai.Service.cart.ICartService;
import com.example.thuongmai.Service.jwt.JwtService;
import com.example.thuongmai.Service.user.IUserService;
import com.example.thuongmai.enums.EnumRoles;
import com.example.thuongmai.model.cart.Cart;
import com.example.thuongmai.model.dto.user.UserRegister;
import com.example.thuongmai.model.jwt.JwtRequest;
import com.example.thuongmai.model.jwt.JwtResponse;
import com.example.thuongmai.model.role.Role;
import com.example.thuongmai.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin("*")
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private IUserService userService;
    @Autowired
    private ICartService cartService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegister userRegister){
        userRegister.setPassword(passwordEncoder.encode(userRegister.getPassword()));
        User user = userService.save(new User(userRegister.getName(), userRegister.getEmail(), userRegister.getUsername(), userRegister.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(2L, EnumRoles.ROLE_USER));
        user.setRoles(roles);
        Cart cart = new Cart();
        cart.setName("Giỏ hàng của " + userRegister.getName());
        cart.setTotalMoney(0L);
        cart.setUser(user);
        cart.setItemCarts(new ArrayList<>());
        cartService.save(cart);
        user.setWallet(0.0);
        user.setLockWallet(0.0);
        user.setDate(LocalDate.now());
        user.setStatus(false);
        user.setAvatar("https://firebasestorage.googleapis.com/v0/b/upload-file-1-5c40f.appspot.com/o/20220115_231405.jpg?alt=media&token=424e36e7-a80e-4c61-b241-d03e5f6a52aa");
        return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest jwtRequest){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtService.generateTokenLogin(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User currentUser = userService.findByUsername(jwtRequest.getUsername()).get();
        currentUser.setStatus(true);
        userService.save(currentUser);
        Optional<Cart> cart = cartService.findByUser(currentUser);
        for (Role role : currentUser.getRoles()){
            if (role.getName() == EnumRoles.ROLE_ADMIN){
                return ResponseEntity.ok(new JwtResponse(currentUser.getName(), userDetails.getUsername(),currentUser.getAvatar(), token, userDetails.getAuthorities(), currentUser.getWallet()));
            }
        }
        int totalItem = cart.get().getItemCarts().size();
        return ResponseEntity.ok(new JwtResponse(currentUser.getName(), userDetails.getUsername(),currentUser.getAvatar(), token, userDetails.getAuthorities(), totalItem, currentUser.getWallet(), currentUser.getLockWallet()));
    }
    @GetMapping("/logout")
    public ResponseEntity<?> logout(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Optional<User> currentUser = userService.findByUsername(username);
        if (!currentUser.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        currentUser.get().setStatus(false);
        return new ResponseEntity<>(userService.save(currentUser.get()),HttpStatus.ACCEPTED);
    }
}
