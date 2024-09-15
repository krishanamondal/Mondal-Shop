package com.mondal.mondal_shop.service.order;

import com.mondal.mondal_shop.enums.OrderStatus;
import com.mondal.mondal_shop.exception.ResourceNotFoundException;
import com.mondal.mondal_shop.model.Cart;
import com.mondal.mondal_shop.model.Order;
import com.mondal.mondal_shop.model.OrderItems;
import com.mondal.mondal_shop.model.Product;
import com.mondal.mondal_shop.repository.OrderRepository;
import com.mondal.mondal_shop.repository.ProductRepository;
import com.mondal.mondal_shop.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;
    private ProductRepository productRepository;
    private CartService cartService;
    @Transactional
    @Override
    public Order placeOrder(Long userId) {

        Cart cart = cartService.getCartByUserId(userId);

        Order order = createOrder(cart);
        List<OrderItems> orderItemsList = createOrderItems(order,cart);
        order.setOrderItems(new HashSet<>(orderItemsList));
        order.setOrderTotalAmount(calculateTotalAmount(orderItemsList));
        Order saveOrder =  orderRepository.save(order);
        cartService.clearCart(cart.getId());

        return saveOrder;
    }
    private Order createOrder(Cart cart){
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return order;
    }

    private List<OrderItems> createOrderItems(Order order, Cart cart) {
        return cart.getItems().stream().map(cartItem -> {
            Product product = cartItem.getProduct();
            product.setInventory(product.getInventory() - cartItem.getQuantity());
            productRepository.save(product);
            return new OrderItems(
                    order,
                    product,
                    cartItem.getQuantity(),
                    cartItem.getUnitPrise()
            );
        }).toList();
    }

    private BigDecimal calculateTotalAmount(List<OrderItems> orderItemsList) {
        return orderItemsList.stream()
                .map(item -> item.getPrise().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("order not found"));
    }
    @Override
    public List<Order> getUserOrders(Long userId){
        return orderRepository.findByUserId(userId);
    }
}
