package com.example.thuongmai.controller;

import com.example.thuongmai.Service.cart.CartService;
import com.example.thuongmai.Service.itemcart.ItemCartService;
import com.example.thuongmai.Service.order.OrderProductService;
import com.example.thuongmai.Service.product.ProductService;
import com.example.thuongmai.Service.shop.ShopService;
import com.example.thuongmai.Service.user.UserService;
import com.example.thuongmai.enums.EnumOrder;
import com.example.thuongmai.model.cart.Cart;
import com.example.thuongmai.model.cart.ItemCart;
import com.example.thuongmai.model.dto.order.OrderProductCreate;
import com.example.thuongmai.model.order.OrderProduct;
import com.example.thuongmai.model.product.Product;
import com.example.thuongmai.model.shop.Shop;
import com.example.thuongmai.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/orders")
public class OrderProductController {
    @Autowired
    private OrderProductService orderProductService;

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private ItemCartService itemCartService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<Iterable<OrderProduct>> findAll() {
        return new ResponseEntity<>(orderProductService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderProduct> getById(@PathVariable Long id) {
        Optional<OrderProduct> orderProduct = orderProductService.findById(id);
        if (!orderProduct.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(orderProduct.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderProductCreate orderProductCreate) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Optional<User> currentUser = userService.findByUsername(username);
        if (!currentUser.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (orderProductCreate.getMoneyOrder() > currentUser.get().getWallet()) {
            return new ResponseEntity<>("không đủ tiền order", HttpStatus.NO_CONTENT);
        }
        Optional<Cart> currentCart = cartService.findByUser(currentUser.get());
        if (!currentCart.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<ItemCart> itemCarts = orderProductCreate.getItemCarts();
        List<Shop> shops = new ArrayList<>();
        for (int i = 0; i < itemCarts.size(); i++) {
            boolean check = false;
            Shop shop = itemCarts.get(i).getProduct().getShop();
            for (int j = 0; j < shops.size(); j++) {
                if (Objects.equals(shop.getId(), shops.get(j).getId())) {
                    check = true;
                }
            }
            if (check) {
                continue;
            }
            shops.add(shop);
        }
        for (int i = 0; i < shops.size(); i++) {
            OrderProduct orderProduct = orderProductService.save(new OrderProduct());
            orderProduct.setEnumOrder(EnumOrder.PENDING);
            orderProduct.setName(orderProductCreate.getName());
            orderProduct.setEmail(orderProductCreate.getEmail());
            orderProduct.setPhone(orderProductCreate.getPhone());
            orderProduct.setAddress(orderProductCreate.getAddress());
            orderProduct.setUser(currentUser.get());
            orderProduct.setItemCarts(new ArrayList<>());
            orderProduct.setMoneyOrder(0L);
            for (int j = 0; j < itemCarts.size(); j++) {
                Shop shop = itemCarts.get(j).getProduct().getShop();
                if (Objects.equals(shop.getId(), shops.get(i).getId())) {
                    Long money = itemCarts.get(j).getProduct().getPrice() * itemCarts.get(j).getQuantity();
                    currentCart.get().setTotalMoney(currentCart.get().getTotalMoney() - money);
                    orderProduct.setMoneyOrder(orderProduct.getMoneyOrder() + money);
                    itemCarts.get(j).setStatus(true);
                    itemCarts.get(j).setOrderProduct(orderProduct);
                    itemCartService.save(itemCarts.get(j));
                }
            }
            orderProductService.save(orderProduct);
        }
        for (int j = 0; j < itemCarts.size(); j++) {
            currentCart.get().getItemCarts().remove(itemCarts.get(j));
        }
        cartService.save(currentCart.get());
        currentUser.get().setLockWallet(currentUser.get().getLockWallet() + orderProductCreate.getMoneyOrder());
        currentUser.get().setWallet(currentUser.get().getWallet() - orderProductCreate.getMoneyOrder());
        return new ResponseEntity<>(userService.save(currentUser.get()), HttpStatus.ACCEPTED);
    }

    @GetMapping("/pending/{shopId}")
    public ResponseEntity<Iterable<OrderProduct>> findPendingOrderByShopId(@PathVariable Long id) {
        return new ResponseEntity<>(orderProductService.findPendingOrderByShopId(id), HttpStatus.OK);
    }

    @GetMapping("/all/{shopId}")
    public ResponseEntity<Page<OrderProduct>> findAllByShopId(@PathVariable Long shopId, @PageableDefault(value = 6) Pageable pageable) {
        return new ResponseEntity<>(orderProductService.findAllByShopId(shopId, pageable), HttpStatus.OK);
    }

    @GetMapping("/confirm/{shopId}")
    public ResponseEntity<Iterable<OrderProduct>> findConfirmByShopId(@PathVariable Long id) {
        return new ResponseEntity<>(orderProductService.findConfirmOrderByShopId(id), HttpStatus.OK);
    }

    @GetMapping("/complete/shopId")
    public ResponseEntity<Iterable<OrderProduct>> findCompleteByShopId(@PathVariable Long id) {
        return new ResponseEntity<>(orderProductService.findCompleteOrderByShopId(id), HttpStatus.OK);
    }

    @PutMapping("/confirm-order/{orderId}")
    public ResponseEntity<?> confirmOrder(@PathVariable Long orderId) {
        Optional<OrderProduct> currentOrderProduct = orderProductService.findById(orderId);
        if (!currentOrderProduct.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Product currentProduct = currentOrderProduct.get().getItemCarts().get(0).getProduct();
        Optional<Shop> currentShop = shopService.findByProducts(currentProduct);
        if (!currentShop.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<ItemCart> itemCarts = currentOrderProduct.get().getItemCarts();
        currentShop.get().setTurnover(currentShop.get().getTurnover() + currentOrderProduct.get().getMoneyOrder());
        User user = currentOrderProduct.get().getUser();
        user.setLockWallet(user.getLockWallet() - currentOrderProduct.get().getMoneyOrder());
        for (int i = 0; i < itemCarts.size(); i++) {
            if (itemCarts.get(i).getQuantity() > itemCarts.get(i).getProduct().getQuantity()) {
                return new ResponseEntity<>("Không đủ sản phẩm trong kho", HttpStatus.ACCEPTED);
            }
            Product product = itemCarts.get(i).getProduct();
            product.setQuantity(product.getQuantity() - itemCarts.get(i).getQuantity());
            product.setCountBuy(product.getCountBuy() + 1);
            productService.save(product);
        }
        currentOrderProduct.get().setEnumOrder(EnumOrder.CONFIRM);
        userService.save(user);
        shopService.save(currentShop.get());
        return new ResponseEntity<>(orderProductService.save(currentOrderProduct.get()), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        Optional<OrderProduct> currentOrder = orderProductService.findById(id);
        if (!currentOrder.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        User user = currentOrder.get().getUser();
        user.setWallet(user.getWallet() + currentOrder.get().getMoneyOrder());
        user.setLockWallet(user.getLockWallet() - currentOrder.get().getMoneyOrder());
        userService.save(user);
        orderProductService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/user/{action}")
    public ResponseEntity<Iterable<OrderProduct>> findAllByUserAndEnumOrder(@PathVariable String action) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Optional<User> currentUser = userService.findByUsername(username);
        if (!currentUser.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Iterable<OrderProduct> orderProducts;
        switch (action) {
            case "Đang chờ":
                orderProducts = orderProductService.findAllByUserAndEnumOrder(currentUser.get(), EnumOrder.PENDING);
                break;
            case "Đã mua":
                orderProducts = orderProductService.findAllByUserAndEnumOrder(currentUser.get(), EnumOrder.COMPLETE);
                break;
            case "Đã xác nhận":
                orderProducts = orderProductService.findAllByUserAndEnumOrder(currentUser.get(), EnumOrder.CONFIRM);
                break;
            default:
                orderProducts = orderProductService.findAllByUser(currentUser.get());
                break;
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/user-complete/{id}")
    public ResponseEntity<OrderProduct> userCompleteOrder(@PathVariable Long id) {
        Optional<OrderProduct> currentOrderProduct = orderProductService.findById(id);
        if (!currentOrderProduct.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        currentOrderProduct.get().setEnumOrder(EnumOrder.COMPLETE);
        return new ResponseEntity<>(orderProductService.save(currentOrderProduct.get()), HttpStatus.ACCEPTED);
    }
}
