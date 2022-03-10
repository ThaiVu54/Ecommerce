package com.example.thuongmai.model.cart;

import com.example.thuongmai.model.order.OrderProduct;
import com.example.thuongmai.model.product.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ItemCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(targetEntity = Product.class)
    private Product product;
    private Long quantity;
    private LocalDate date;
    private String comment;
    private Boolean status;
    @JsonIgnore
    @ManyToOne(targetEntity = Cart.class)
    private Cart cart;
    @JsonIgnore
    @ManyToOne(targetEntity = OrderProduct.class)
    private OrderProduct orderProduct;
}
