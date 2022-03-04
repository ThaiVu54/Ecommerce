package com.example.thuongmai.model.shop;

import com.example.thuongmai.enums.EnumFollowShop;
import com.example.thuongmai.model.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyShop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(targetEntity = Shop.class)
    private Shop shop;
    private EnumFollowShop followShopOrOwner;
    @JsonIgnore
    @ManyToOne(targetEntity = User.class)
    private User user;
}
