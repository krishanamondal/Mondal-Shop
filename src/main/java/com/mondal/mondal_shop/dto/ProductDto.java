package com.mondal.mondal_shop.dto;

import com.mondal.mondal_shop.model.Category;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal prise;
    private int inventory;
    private String description;
    private Category category;
    private List<ImageDto> images;

}
