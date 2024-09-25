package com.mondal.mondal_shop.service.cart;

import com.mondal.mondal_shop.model.Cart;
import com.mondal.mondal_shop.model.User;

import java.math.BigDecimal;

public interface CartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);

    Long initializeNewCart(User user);

    Cart getCartByUserId(Long userId);
}
