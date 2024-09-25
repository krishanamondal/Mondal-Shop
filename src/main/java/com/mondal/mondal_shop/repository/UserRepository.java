package com.mondal.mondal_shop.repository;

import com.mondal.mondal_shop.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByusername(String email);

    Optional<User> findByusername(String username);
    @Transactional
    @Modifying
    @Query("update User u set u.password = ?2 where u.username = ?1")
    void updatePassword(String email, String password);
//    @Transactional
//    @Modifying
//    User updateUserById(Long userId);
}
