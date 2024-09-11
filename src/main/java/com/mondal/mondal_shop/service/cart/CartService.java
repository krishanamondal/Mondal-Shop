package com.mondal.mondal_shop.service.cart;

import com.mondal.mondal_shop.model.Cart;

import java.math.BigDecimal;

public interface CartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrise(Long id);
}
