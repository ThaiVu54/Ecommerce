package com.example.thuongmai.model;

import com.example.thuongmai.model.shop.Shop;
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
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double price;
    private Long quantity;
    private Long countBuy;
    @Lob
    @Column(columnDefinition = "varchar(4000)")
    private String description;
    private LocalDate dayUpdate;
    @ManyToOne(targetEntity = Origin.class)
    private Origin origin;
    private String brand;
    @OneToMany(mappedBy = "product")
    private List<Image> images;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "products_categories",
    joinColumns = {@JoinColumn(name = "product_id")},
    inverseJoinColumns = {@JoinColumn(name = "category_id")})
    private List<Category> categories;
    @ManyToOne(targetEntity = Shop.class)
    private Shop shop;
    @OneToMany(mappedBy = "product")
    private List<Comment> comments;
}
