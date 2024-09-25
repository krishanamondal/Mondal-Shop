package com.mondal.mondal_shop.controller;


import com.mondal.mondal_shop.dto.AuthResponseDto;
import com.mondal.mondal_shop.request.CreateUserRequest;
import com.mondal.mondal_shop.service.auth.AuthService;
import com.mondal.mondal_shop.service.auth.JwtService;
import com.mondal.mondal_shop.service.auth.RefreshTokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/auth/")
public class AuthController {
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;

    public AuthController(AuthService authService, RefreshTokenService refreshTokenService, JwtService jwtService) {
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
        this.jwtService = jwtService;
    }
    @PostMapping(value = "/register")
    public ResponseEntity<AuthResponseDto> register(@Valid @RequestBody CreateUserRequest request){
        return ResponseEntity.ok(authService.register(request));
    }

//    @PostMapping(value = "/login")
//    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginRequestDto loginRequestDto){
//        return ResponseEntity.ok(authService.login(loginRequestDto));
//    }
//    @PostMapping(value = "/refresh")
//    public ResponseEntity<AuthResponseDto> refreshToken(@RequestBody RefreshTokenRequestDto request) {
//        RefreshToken refreshToken = refreshTokenService.verifyRefreshToken(request.getRefreshToken());
//        User user = refreshToken.getUser();
//        String accessToken = this.jwtService.generateToken(user);
//        return ResponseEntity.ok(AuthResponseDto.builder()
//                .refreshToken(refreshToken.getRefreshToken())
//                .accessToken(accessToken)
//                .build());
//
//    }

    }

