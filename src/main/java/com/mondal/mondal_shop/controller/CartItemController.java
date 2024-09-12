package com.mondal.mondal_shop.controller;

import com.mondal.mondal_shop.exception.ResourceNotFoundException;
import com.mondal.mondal_shop.response.ApiResponse;
import com.mondal.mondal_shop.service.cart.CartItemService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@AllArgsConstructor
@RestController
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {
    private final CartItemService cartItemService;

    @PostMapping("/item/add")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestParam Long cartId,
                                                     @RequestParam Long productId,
                                                     @RequestParam Integer quantity){
        try {
            cartItemService.addCartItem(cartId,productId,quantity);
            return ResponseEntity.ok(new ApiResponse("Add Item Success",null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/cart/{cartId}/item/{itemId}/remove")
    public ResponseEntity<ApiResponse> removeItemFormCart(@PathVariable Long cartId,@PathVariable Long itemId){
        try {
            cartItemService.removeItemFormCart(cartId,itemId);
            return ResponseEntity.ok(new ApiResponse("cart remove success",null));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
