package com.example.thuongmai.model.dto.cart;

import com.example.thuongmai.model.product.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemCartForm {
    private Product product;
    private Long quantity;
    private String comment;
}
