package com.mondal.mondal_shop.service.auth;

import com.mondal.mondal_shop.dto.AuthResponseDto;
import com.mondal.mondal_shop.model.User;
import com.mondal.mondal_shop.model.UserRole;
import com.mondal.mondal_shop.repository.UserRepository;
import com.mondal.mondal_shop.request.CreateUserRequest;
import com.mondal.mondal_shop.request.LoginRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;

    public AuthService(PasswordEncoder passwordEncoder, UserRepository userRepository, JwtService jwtService, RefreshTokenService refreshTokenService, AuthenticationManager authenticationManager) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponseDto register(CreateUserRequest request){
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.USER)
                .build();
        User savedUser = userRepository.save(user);
        var accessToken = jwtService.generateToken(savedUser);
        var refreshToken = refreshTokenService.createRefreshToken(user.getUsername());
        return AuthResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();
    }
//    public AuthResponseDto login(LoginRequest loginRequestDto){
//        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(),
//                                                                                    loginRequestDto.getPassword())) ;
//        User user = userRepository.findByusername(loginRequestDto.getEmail()).orElseThrow(() -> new UsernameNotFoundException("user not found"));
//        var accessToken = jwtService.generateToken(user);
//        var refreshToken = refreshTokenService.createRefreshToken(loginRequestDto.getEmail());
//
//        return AuthResponseDto.builder()
//                .accessToken(accessToken)
//                .refreshToken(refreshToken.getRefreshToken())
//                .build();
//    }
}
