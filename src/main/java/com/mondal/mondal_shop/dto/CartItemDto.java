package com.mondal.mondal_shop.dto;

import com.mondal.mondal_shop.model.Product;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class CartItemDto {
    private Long itemId;
    private Integer quantity;
    private BigDecimal unitPrise;
    private ProductDto product;
}
