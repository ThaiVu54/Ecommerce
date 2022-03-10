package com.example.thuongmai.Service.user;

import com.example.thuongmai.model.user.User;
import com.example.thuongmai.model.user.UserPrincipal;
import com.example.thuongmai.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserService implements IUserService{
    @Autowired private IUserRepository userRepository;
    @Override
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Page<User> findAllPage(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Optional<User> findUserByMyShopId(Long myShopId) {
        return userRepository.findUserByMyShopId(myShopId);
    }

    @Override
    public Iterable<User> findAllByUserBuyProduct(Long productId) {
        return userRepository.findAllByUserBuyProduct(productId);
    }

    @Override
    public Optional<User> findByShopId(Long shopId) {
        return userRepository.findByShopId(shopId);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (!userOptional.isPresent()){
            throw new UsernameNotFoundException(username);
        }
        return UserPrincipal.build(userOptional.get());
    }
}
