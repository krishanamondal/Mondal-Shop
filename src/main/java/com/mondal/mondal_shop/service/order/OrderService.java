package com.mondal.mondal_shop.service.order;

import com.mondal.mondal_shop.dto.OrderDto;
import com.mondal.mondal_shop.model.Order;

import java.util.List;

public interface OrderService {
    Order placeOrder(Long userId);
    OrderDto getOrder(Long orderId);
    List<OrderDto> getUserOrders(Long userId);
}
