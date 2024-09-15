package com.mondal.mondal_shop.dto;

import com.mondal.mondal_shop.enums.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Data
public class OrderDto {
    private Long orderId;
    private LocalDate orderDate;
    private BigDecimal orderTotalAmount;

    private OrderStatus orderStatus;

    private List<OrderItemsDto> itemsDtos;
}
