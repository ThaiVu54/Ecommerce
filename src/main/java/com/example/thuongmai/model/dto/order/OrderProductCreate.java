package com.example.thuongmai.model.dto.order;

import com.example.thuongmai.model.cart.ItemCart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductCreate {
    private String name;
    private String phone;
    private String email;
    private String address;
    private Double moneyOrder;
    private List<ItemCart> itemCarts;
}
