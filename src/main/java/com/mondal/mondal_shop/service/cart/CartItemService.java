package com.mondal.mondal_shop.service.cart;

import com.mondal.mondal_shop.model.CartItem;
import org.apache.logging.log4j.message.LoggerNameAwareMessage;

public interface CartItemService {
    void addCartItem(Long cartId,Long productId,int quantity);
    void removeItemFormCart(Long cartId,Long productId);
    void updateItemQuantity(Long cartId,Long productId,int quantity);

    CartItem getCartItem(Long cartId, Long productId);
}
