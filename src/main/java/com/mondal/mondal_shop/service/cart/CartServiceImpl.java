package com.mondal.mondal_shop.service.cart;

import com.mondal.mondal_shop.exception.ResourceNotFoundException;
import com.mondal.mondal_shop.model.Cart;
import com.mondal.mondal_shop.model.CartItem;
import com.mondal.mondal_shop.repository.CartItemRepository;
import com.mondal.mondal_shop.repository.CartRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService{
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    @Override
    public Cart getCart(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart Not Found"));
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
    }

    @Override
    public void clearCart(Long id) {
    Cart cart = getCart(id);
    cartItemRepository.deleteAllByCartId(id);
    cart.getItems().clear();
    cartRepository.deleteById(id);
    }

    @Override
    public BigDecimal getTotalPrise(Long id) {
        Cart cart = getCart(id);

        return cart.getItems()
                .stream()
                .map(CartItem::getTotalPrise)
                .reduce(BigDecimal.ZERO,BigDecimal::add);
    }
}
