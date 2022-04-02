package com.example.thuongmai.controller;

import com.example.thuongmai.Service.image.IImageService;
import com.example.thuongmai.Service.product.IProductService;
import com.example.thuongmai.model.dto.product.ProductCreate;
import com.example.thuongmai.model.dto.product.ProductEdit;
import com.example.thuongmai.model.image.Image;
import com.example.thuongmai.model.product.Product;
import com.example.thuongmai.model.shop.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private IProductService productService;
    @Autowired
    private IImageService iImageService;

    @GetMapping
    public ResponseEntity<Iterable<Product>> findAll() {
        return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<Product>> findAllPage(@PageableDefault(value = 8) Pageable pageable) {
        return new ResponseEntity<>(productService.findAllPage(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Product> productOptional = productService.findById(id);
        if (productOptional.isPresent()) {
            return new ResponseEntity<>(productOptional.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //them product
    @PostMapping
    public ResponseEntity<Product> save(@RequestBody ProductCreate productCreate) {
        Product product = productService.save(new Product());
        product.setName(productCreate.getName());
        product.setPrice(productCreate.getPrice());
        product.setQuantity(productCreate.getQuantity());
        product.setDescription(product.getDescription());
        product.setOrigin(productCreate.getOrigin());
        product.setBrand(productCreate.getBrand());
        if (productCreate.getImages().size() > 0) {
            product.setImages(new ArrayList<>());
            for (String imageUrl : productCreate.getImages()) {
                Image image = iImageService.save(new Image(imageUrl, product));
                product.getImages().add(image);
            }
        }
        product.setCategories(productCreate.getCategories());
        product.setShop(productCreate.getShop());
        product.setCountBuy(0L);
        product.setDayUpdate(LocalDate.now());
        return new ResponseEntity<>(productService.save(product), HttpStatus.ACCEPTED);
    }

    //cap nhat product
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody ProductEdit productEdit) {
        Optional<Product> currentProduct = productService.findById(id);
        if (!currentProduct.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Product product = currentProduct.get();
        product.setName(productEdit.getName());
        product.setQuantity(product.getQuantity() + productEdit.getQuantity());
        product.setPrice(productEdit.getPrice());
        product.setDescription(productEdit.getDescription());
        product.setOrigin(productEdit.getOrigin());
        product.setCategories(productEdit.getCategories());
        product.setBrand(productEdit.getBrand());
        return new ResponseEntity<>(productService.save(product), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        productService.deleteById(id);
        return new ResponseEntity<>("Xóa thành công", HttpStatus.NO_CONTENT);
    }

    @PutMapping("/my-shop")
    public ResponseEntity<Page<Product>> findByShopId(@RequestBody Shop shop, @PageableDefault(value = 8) Pageable pageable) {
        return new ResponseEntity<>(productService.findAllByShop(pageable, shop), HttpStatus.OK);
    }
}
