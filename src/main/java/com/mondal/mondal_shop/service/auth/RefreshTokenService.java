package com.mondal.mondal_shop.service.auth;


import com.mondal.mondal_shop.model.RefreshToken;
import com.mondal.mondal_shop.model.User;
import com.mondal.mondal_shop.repository.RefreshTokenRepository;
import com.mondal.mondal_shop.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(UserRepository userRepository, RefreshTokenRepository refreshTokenRepository) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
    }
    public RefreshToken createRefreshToken(String username){
       User user=  userRepository.findByusername(username)
               .orElseThrow(() -> new UsernameNotFoundException(" User Not Found"));
        RefreshToken refreshToken = user.getRefreshToken();

        if (refreshToken == null){
            long refreshTokenValidity = 5 * 60 * 60 *100;
            refreshToken = RefreshToken.builder()
                    .refreshToken(UUID.randomUUID().toString())
                    .expirationTime(Instant.now().plusMillis(refreshTokenValidity))
                    .user(userRepository.findByusername(username).orElseThrow(() -> new UsernameNotFoundException("User Not found this  "+username)))
                    .build();

            refreshTokenRepository.save(refreshToken);
        }
        return refreshToken;
    }
    public RefreshToken verifyRefreshToken(String refreshToken){
        RefreshToken refToken = refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("refresh Token Dosn't Exists"));

        if (refToken.getExpirationTime().compareTo(Instant.now()) < 0 ){
            refreshTokenRepository.delete(refToken);
            throw new RuntimeException("Refresh Token Expire");
        }
        return refToken;
    }
}
