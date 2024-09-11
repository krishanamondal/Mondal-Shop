package com.mondal.mondal_shop.service.cart;


import com.mondal.mondal_shop.model.Cart;
import com.mondal.mondal_shop.model.CartItem;
import com.mondal.mondal_shop.model.Product;
import com.mondal.mondal_shop.repository.CartItemRepository;
import com.mondal.mondal_shop.repository.CartRepository;
import com.mondal.mondal_shop.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService{
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductService productService;
    private final CartService cartService;
    @Override
    public void addCartItem(Long cartId, Long productId, int quantity) {
//    1 get the cart
//    2 get the product
//    3 check if the product already in the cart
//    4 if yes then increase the quantity with the requested quantity
//     5 if no the initiate a new  cartItem entry.
        Cart cart = cartService.getCart(cartId);
        Product product = productService.getProductById(productId);
        CartItem cartItem = cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId() .equals(productId))
                .findFirst().orElse(new CartItem());
        if (cartItem.getId() == null){
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrise(product.getPrise());
        }else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }
        cartItem.setTotalPrise();
        cart.addItem(cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);

    }

    @Override
    public void removeItemFormCart(Long cartId, Long productId) {

    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {

    }
}
