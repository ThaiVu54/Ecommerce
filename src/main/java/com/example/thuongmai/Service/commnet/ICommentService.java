package com.example.thuongmai.Service.commnet;

import com.example.thuongmai.Service.IGeneralService;
import com.example.thuongmai.model.comment.Comment;
import com.example.thuongmai.model.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICommentService extends IGeneralService<Comment> {
    Page<Comment> findAllByProduct(Product product, Pageable pageable);
}
