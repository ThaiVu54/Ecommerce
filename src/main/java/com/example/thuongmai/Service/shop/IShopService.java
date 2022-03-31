package com.example.thuongmai.Service.shop;

import com.example.thuongmai.Service.IGeneralService;
import com.example.thuongmai.model.product.Product;
import com.example.thuongmai.model.shop.Shop;

import java.util.Optional;

public interface IShopService extends IGeneralService<Shop> {
    Optional<Shop> findByProducts(Product product);
}
