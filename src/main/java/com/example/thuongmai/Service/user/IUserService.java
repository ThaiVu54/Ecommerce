package com.example.thuongmai.Service.user;

import com.example.thuongmai.Service.IGeneralService;
import com.example.thuongmai.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface IUserService extends IGeneralService<User>, UserDetailsService {
    Optional<User> findByUsername(String username);
    Page<User> findAllPage(Pageable pageable);
    Optional<User> findUserByMyShopId(Long myShopId);
    Iterable<User> findAllByUserBuyProduct(Long productId);
    Optional<User> findByShopId(Long shopId);
}
