package com.mondal.mondal_shop.controller;

import com.mondal.mondal_shop.dto.AuthResponseDto;
import com.mondal.mondal_shop.dto.UserDto;
import com.mondal.mondal_shop.exception.AlreadyExistsException;
import com.mondal.mondal_shop.exception.ResourceNotFoundException;
import com.mondal.mondal_shop.model.User;
import com.mondal.mondal_shop.request.CreateUserRequest;
import com.mondal.mondal_shop.request.LoginRequest;
import com.mondal.mondal_shop.request.UserUpdateRequest;
import com.mondal.mondal_shop.response.ApiResponse;
import com.mondal.mondal_shop.service.auth.AuthService;
import com.mondal.mondal_shop.service.auth.JwtService;
import com.mondal.mondal_shop.service.auth.RefreshTokenService;
import com.mondal.mondal_shop.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId){
        try {
            User user = userService.getUserById(userId);
            UserDto userDto = userService.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse("Success",userDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> createUser(@Valid @RequestBody CreateUserRequest request){
        try {
            AuthResponseDto user = userService.createUser(request);

            return ResponseEntity.ok(new ApiResponse(null,user));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping(value = "/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(userService.login(loginRequest));
    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UserUpdateRequest request,@PathVariable Long userId){
        try {
            User user = userService.updateUser(request, userId);
            UserDto userDto = userService.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse("user update successfully",userDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId){
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok(new ApiResponse("delete User Success",userId));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }

    }

    @PutMapping("/admin/{id}")
    public ResponseEntity<ApiResponse> userToAdmin(@PathVariable Long id){
        User user= userService.getUserById(id);
        UserDto userDto = userService.convertUserToDto(user);
        User admin = userService.makeAdmin(userDto);
        return ResponseEntity.ok(new ApiResponse("success",admin.getRole()));
    }
}
