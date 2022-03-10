package com.example.thuongmai.controller;

import com.example.thuongmai.Service.cart.ICartService;
import com.example.thuongmai.Service.itemcart.IItemCartService;
import com.example.thuongmai.Service.user.IUserService;
import com.example.thuongmai.model.cart.Cart;
import com.example.thuongmai.model.cart.ItemCart;
import com.example.thuongmai.model.dto.cart.ItemCartForm;
import com.example.thuongmai.model.product.Product;
import com.example.thuongmai.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/carts")
public class CartController {
    @Autowired
    private ICartService cartService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IItemCartService iItemCartService;
    @GetMapping
    public ResponseEntity<Iterable<Cart>> findAll(){
        return new ResponseEntity<>(cartService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Cart> findById(@PathVariable Long id){
        Optional<Cart> cartOptional = cartService.findById(id);
        if (!cartOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cartOptional.get(),HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody ItemCartForm itemCartForm){
        if (itemCartForm.getProduct().getQuantity()< itemCartForm.getQuantity()){
            return new ResponseEntity<>("sản phẩm không đủ số lượng", HttpStatus.NO_CONTENT);
        }
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Optional<User> userOptional = userService.findByUsername(username);
        if (!userOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<Cart> cartOptional = cartService.findByUser(userOptional.get());
        if (!cartOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Long totalMoney = cartOptional.get().getTotalMoney();
        boolean check = false;
        for (int i = 0; i < cartOptional.get().getItemCarts().size(); i++){
            ItemCart itemCart = cartOptional.get().getItemCarts().get(i);
            Product product = itemCartForm.getProduct();
            if (itemCart.getProduct().getId() == product.getId() && itemCart.getStatus() == false){
                Long money = itemCart.getQuantity() * itemCart.getProduct().getPrice();
                totalMoney -= money;
                Long quantity = itemCartForm.getQuantity();
                itemCart.setQuantity(quantity);
                money = product.getPrice() * quantity;
                totalMoney += money;
                itemCart.setDate(LocalDate.now());
                cartOptional.get().setTotalMoney(totalMoney);
                check = true;
            }
            itemCart.setCart(cartOptional.get());
            iItemCartService.save(itemCart);
        }
        if (check){
            return new ResponseEntity<>(cartService.save(cartOptional.get()), HttpStatus.ACCEPTED);
        }
        ItemCart itemCart = iItemCartService.save(new ItemCart());
        itemCart.setCart(cartOptional.get());
        itemCart.setComment(itemCartForm.getComment());
        itemCart.setProduct(itemCartForm.getProduct());
        itemCart.setQuantity(itemCartForm.getQuantity());
        itemCart.setStatus(false);
        itemCart.setDate(LocalDate.now());
        cartOptional.get().getItemCarts().add(itemCart);
        cartOptional.get().setTotalMoney(cartOptional.get().getTotalMoney()+(itemCartForm.getProduct().getPrice() * itemCartForm.getQuantity()));
        return new ResponseEntity<>(cartService.save(cartOptional.get()), HttpStatus.ACCEPTED);
    }

    @GetMapping("/by-user")
    public ResponseEntity<?> findByUser(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Optional<User> userOptional = userService.findByUsername(username);
        if (!userOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<Cart> cartOptional = cartService.findByUser(userOptional.get());
        if (!cartOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cartOptional.get(),HttpStatus.OK);
    }

    @PutMapping("/item/{id}")
    public ResponseEntity<?> deleteItemCartById(@PathVariable Long id){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Optional<User> userOptional = userService.findByUsername(username);
        if (!userOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<Cart> cartOptional = cartService.findByUser(userOptional.get());
        if (!cartOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<ItemCart> itemCartOptional = iItemCartService.findById(id);
        if (!itemCartOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Long money = cartOptional.get().getTotalMoney() - (itemCartOptional.get().getQuantity() * itemCartOptional.get().getProduct().getPrice());
        cartOptional.get().setTotalMoney(money);
        cartOptional.get().getItemCarts().remove(itemCartOptional.get());
        iItemCartService.deleteById(id);
        cartService.save(cartOptional.get());
        return new ResponseEntity<>(cartOptional.get(), HttpStatus.OK);
    }
}
