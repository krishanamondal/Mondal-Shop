package com.mondal.mondal_shop.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemsDto {
    private Long productId;
    private String productName;
    private String productBrand;
    private int quantity;
    private BigDecimal prise;
}
