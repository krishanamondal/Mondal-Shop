package com.mondal.mondal_shop.dto;

import com.mondal.mondal_shop.model.Cart;
import com.mondal.mondal_shop.model.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private List<OrderDto> orders;
    private CartDto cart;


}

