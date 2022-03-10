package com.example.thuongmai.repository;


import com.example.thuongmai.model.cart.Cart;
import com.example.thuongmai.model.cart.ItemCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IItemCartRepository extends JpaRepository<ItemCart, Long> {
    Iterable<ItemCart> findAllByCart(Cart cart);
}
