package com.example.thuongmai.Service.cart;

import com.example.thuongmai.model.cart.Cart;
import com.example.thuongmai.model.user.User;
import com.example.thuongmai.repository.ICartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService implements ICartService{
    @Autowired
    ICartRepository cartRepository;
    @Override
    public Optional<Cart> findByUser(User user) {
        return cartRepository.findByUser(user);
    }

    @Override
    public Iterable<Cart> findAll() {
        return cartRepository.findAll();
    }

    @Override
    public Optional<Cart> findById(Long id) {
        return cartRepository.findById(id);
    }

    @Override
    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public void deleteById(Long id) {
cartRepository.deleteById(id);
    }
}
