package com.mondal.mondal_shop.repository;

import com.mondal.mondal_shop.model.Cart;
import com.mondal.mondal_shop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
   Cart findByUserId(Long userId);
}
