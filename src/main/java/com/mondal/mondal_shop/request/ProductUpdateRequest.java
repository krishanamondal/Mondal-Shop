package com.mondal.mondal_shop.request;

import com.mondal.mondal_shop.model.Category;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductUpdateRequest {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal prise;
    private int inventory;
    private String description;
    private Category category;

}
