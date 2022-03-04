package com.example.thuongmai.model;

import com.example.thuongmai.model.shop.Shop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RoomChat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne(targetEntity = Shop.class)
    private Shop shop;
    @OneToMany(mappedBy = "roomChat")
    private List<Message> messages;
    @ManyToOne(targetEntity = User.class)
    private User user;
}
