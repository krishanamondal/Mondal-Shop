package com.mondal.mondal_shop.service.order;

import com.mondal.mondal_shop.model.Order;

import java.util.List;

public interface OrderService {
    Order placeOrder(Long userId);
    Order getOrder(Long orderId);

    List<Order> getUserOrders(Long userId);
}
