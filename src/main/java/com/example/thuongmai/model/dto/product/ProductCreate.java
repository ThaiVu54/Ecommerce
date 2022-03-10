package com.example.thuongmai.model.dto.product;
import com.example.thuongmai.model.category.Category;
import com.example.thuongmai.model.origin.Origin;
import com.example.thuongmai.model.shop.Shop;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreate {
    private String name;
    private Long price;
    private Long quantity;
    private String description;
    private Origin origin;
    private String brand;
    private List<String> images;
    private List<Category> categories;
    private Shop shop;
}
