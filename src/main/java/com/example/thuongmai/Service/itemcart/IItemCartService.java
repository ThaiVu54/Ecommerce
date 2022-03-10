package com.example.thuongmai.Service.itemcart;


import com.example.thuongmai.Service.IGeneralService;
import com.example.thuongmai.model.cart.Cart;
import com.example.thuongmai.model.cart.ItemCart;

public interface IItemCartService extends IGeneralService<ItemCart> {
    Iterable<ItemCart> findAllByCart(Cart cart);
}
