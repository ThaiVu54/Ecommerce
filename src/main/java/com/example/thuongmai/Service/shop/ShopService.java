package com.example.thuongmai.Service.shop;

import com.example.thuongmai.model.product.Product;
import com.example.thuongmai.model.shop.Shop;
import com.example.thuongmai.repository.IShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShopService implements IShopService {
    @Autowired
    private IShopRepository shopRepository;

    @Override
    public Iterable<Shop> findAll() {
        return shopRepository.findAll();
    }

    @Override
    public Optional<Shop> findById(Long id) {
        return shopRepository.findById(id);
    }

    @Override
    public Shop save(Shop shop) {
        return shopRepository.save(shop);
    }

    @Override
    public void deleteById(Long id) {
        shopRepository.deleteById(id);
    }

    @Override
    public Optional<Shop> findByProducts(Product product) {
        return shopRepository.findByProducts(product);
    }
}
