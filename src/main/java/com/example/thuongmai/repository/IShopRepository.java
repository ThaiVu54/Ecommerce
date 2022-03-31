package com.example.thuongmai.repository;

import com.example.thuongmai.model.product.Product;
import com.example.thuongmai.model.shop.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IShopRepository extends JpaRepository<Shop, Long> {
    Optional<Shop> findByProducts(Product product);
}
