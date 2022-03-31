package com.example.thuongmai.Service.myshop;

import com.example.thuongmai.model.shop.MyShop;
import com.example.thuongmai.repository.myshop.IMyShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyShopService implements IMyShopService {
    @Autowired
    private IMyShopRepository myShopRepository;

    @Override
    public Iterable<MyShop> findAll() {
        return myShopRepository.findAll();
    }

    @Override
    public Optional<MyShop> findById(Long id) {
        return myShopRepository.findById(id);
    }

    @Override
    public MyShop save(MyShop myShop) {
        return myShopRepository.save(myShop);
    }

    @Override
    public void deleteById(Long id) {
        myShopRepository.deleteById(id);
    }
}
