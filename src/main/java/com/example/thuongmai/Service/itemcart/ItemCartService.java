package com.example.thuongmai.Service.itemcart;

import com.example.thuongmai.model.cart.Cart;
import com.example.thuongmai.model.cart.ItemCart;
import com.example.thuongmai.repository.IItemCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemCartService implements IItemCartService {
    @Autowired
    private IItemCartRepository iItemCartRepository;

    @Override
    public Iterable<ItemCart> findAllByCart(Cart cart) {
        return iItemCartRepository.findAllByCart(cart);
    }

    @Override
    public Iterable<ItemCart> findAll() {
        return iItemCartRepository.findAll();
    }

    @Override
    public Optional<ItemCart> findById(Long id) {
        return iItemCartRepository.findById(id);
    }

    @Override
    public ItemCart save(ItemCart itemCart) {
        return iItemCartRepository.save(itemCart);
    }

    @Override
    public void deleteById(Long id) {
        iItemCartRepository.deleteById(id);
    }
}
