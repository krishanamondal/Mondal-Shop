package com.mondal.mondal_shop.dto;

import com.mondal.mondal_shop.model.Cart;
import com.mondal.mondal_shop.model.Order;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<OrderDto> orders;
    private CartDto cart;
}
