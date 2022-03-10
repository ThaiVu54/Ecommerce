package com.example.thuongmai.model.dto.product;

import com.example.thuongmai.model.category.Category;
import com.example.thuongmai.model.origin.Origin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductEdit {
    private String name;
    private Long price;
    private String description;
    private Origin origin;
    private String brand;
    private Long quantity;
    private List<Category> categories;
}
