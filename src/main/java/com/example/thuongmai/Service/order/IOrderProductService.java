package com.example.thuongmai.Service.order;


import com.example.thuongmai.Service.IGeneralService;
import com.example.thuongmai.enums.EnumOrder;
import com.example.thuongmai.model.order.OrderProduct;
import com.example.thuongmai.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IOrderProductService extends IGeneralService<OrderProduct> {
    Iterable<OrderProduct> findPendingOrderByShopId(Long shopId);
    Page<OrderProduct> findAllByShopId(Long shopId, Pageable pageable);
    Iterable<OrderProduct> findConfirmOrderByShopId(Long shopId);
    Iterable<OrderProduct> findCompleteOrderByShopId(Long shopId);
    Iterable<OrderProduct> findAllByUserAndEnumOrder(User user, EnumOrder enumOrder);
    Iterable<OrderProduct> findAllByUser(User user);
}
