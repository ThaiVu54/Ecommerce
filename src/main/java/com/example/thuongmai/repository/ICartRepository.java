package com.example.thuongmai.repository;

import com.example.thuongmai.model.cart.Cart;
import com.example.thuongmai.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
}
