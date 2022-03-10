package com.example.thuongmai.model.shop;

import com.example.thuongmai.enums.EnumShop;
import com.example.thuongmai.enums.EnumShopType;
import com.example.thuongmai.model.product.Product;
import com.example.thuongmai.model.roomchat.RoomChat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Lob
    @Column(columnDefinition = "varchar(255) default 'default-avatar.png'")
    private String avatar;
    private LocalDate startOpen;
    private Long viewShop;
    private Long countFollow;
    private Long turnover;
    private EnumShop status;
    private EnumShopType type;
    private String description;
    @JsonIgnore
    @OneToMany(mappedBy = "shop")
    private List<Product> products;
    @JsonIgnore
    @OneToMany(mappedBy = "shop")
    private List<RoomChat> roomChats;
}
