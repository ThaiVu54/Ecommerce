package com.example.thuongmai.model.dto.comment;

import com.example.thuongmai.model.product.Product;
import com.example.thuongmai.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentForm {
    private String comment;
    private Long score;
    private User user;
    private Product product;
}
