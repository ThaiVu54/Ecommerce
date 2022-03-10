package com.example.thuongmai.Service.cart;

import com.example.thuongmai.Service.IGeneralService;
import com.example.thuongmai.model.cart.Cart;
import com.example.thuongmai.model.user.User;

import java.util.Optional;

public interface ICartService extends IGeneralService<Cart> {
    Optional<Cart> findByUser(User user);

}
