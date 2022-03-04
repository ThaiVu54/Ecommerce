package com.example.thuongmai.model;

import com.example.thuongmai.enums.EnumOrder;
import com.example.thuongmai.model.cart.ItemCart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phone;
    private String address;
    private String email;
    private Long moneyOrder;
    private EnumOrder enumOrder;
    @OneToMany(mappedBy = "orderProduct")
    private List<ItemCart> itemCarts;
    @ManyToOne(targetEntity = User.class)
    private User user;
}
