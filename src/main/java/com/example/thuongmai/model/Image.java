package com.example.thuongmai.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    @Column(columnDefinition = "varchar(255) default 'default-avatar.png'")
    private String url;
    @JsonIgnore
    @ManyToOne(targetEntity = Product.class)
    private Product product;

    public Image(String imageUrl, Product product) {
        this.url = imageUrl;
        this.product = product;
    }
}
