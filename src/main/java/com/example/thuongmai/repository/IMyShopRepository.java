package com.example.thuongmai.repository;

import com.example.thuongmai.model.shop.MyShop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMyShopRepository extends JpaRepository<MyShop, Long> {

}
