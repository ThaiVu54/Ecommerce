package com.example.thuongmai.Service.product;

import com.example.thuongmai.Service.IGeneralService;
import com.example.thuongmai.model.product.Product;
import com.example.thuongmai.model.shop.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductService extends IGeneralService<Product> {
    Page<Product> findAllByShop(Pageable pageable, Shop shop);

    Page<Product> findAllPage(Pageable pageable);
}
