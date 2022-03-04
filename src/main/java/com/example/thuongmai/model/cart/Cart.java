package com.example.thuongmai.model.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import com.example.thuongmai.model.User;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double totalMoney;
    @OneToMany(mappedBy = "cart")
    private List<ItemCart> itemCarts;
    @OneToOne(targetEntity = User.class)
    private User user;
}
