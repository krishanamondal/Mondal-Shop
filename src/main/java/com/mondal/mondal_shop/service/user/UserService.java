package com.mondal.mondal_shop.service.user;

import com.mondal.mondal_shop.dto.AuthResponseDto;
import com.mondal.mondal_shop.dto.UserDto;
import com.mondal.mondal_shop.model.User;
import com.mondal.mondal_shop.request.CreateUserRequest;
import com.mondal.mondal_shop.request.LoginRequest;
import com.mondal.mondal_shop.request.UserUpdateRequest;

public interface UserService {

    User getUserById(Long userId);
    AuthResponseDto createUser(CreateUserRequest request);
    AuthResponseDto login(LoginRequest request);
    User updateUser(UserUpdateRequest request,Long userId);
    void deleteUser(Long userId);
    User makeAdmin(UserDto userDto);

    UserDto convertUserToDto(User user);
}
