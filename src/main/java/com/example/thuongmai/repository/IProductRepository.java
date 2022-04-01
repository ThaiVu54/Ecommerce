package com.example.thuongmai.repository;

import com.example.thuongmai.model.product.Product;
import com.example.thuongmai.model.shop.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {
 Page<Product> findAllByShop(Pageable pageable, Shop shop);
}
