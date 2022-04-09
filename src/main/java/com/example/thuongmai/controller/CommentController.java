package com.example.thuongmai.controller;

import com.example.thuongmai.Service.commnet.ICommentService;
import com.example.thuongmai.model.comment.Comment;
import com.example.thuongmai.model.dto.comment.CommentForm;
import com.example.thuongmai.model.product.Product;
import com.example.thuongmai.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@CrossOrigin("*")
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private ICommentService commentService;

    @GetMapping("/page")
    public ResponseEntity<Page<Comment>> findAllByProduct(@RequestBody Product product, @PageableDefault(value = 3) Pageable pageable) {
        return new ResponseEntity<>(commentService.findAllByProduct(product, pageable), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Comment> save(@RequestBody CommentForm commentForm) {
        Comment comment = new Comment();
        comment.setComment(commentForm.getComment());
        comment.setDate(LocalDate.now());
        comment.setScore(commentForm.getScore());
        comment.setUser(commentForm.getUser());
        comment.setProduct(commentForm.getProduct());
        return new ResponseEntity<>(commentService.save(comment), HttpStatus.ACCEPTED);
    }
}
