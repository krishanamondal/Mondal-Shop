package com.mondal.mondal_shop.service.user;

import com.mondal.mondal_shop.model.User;
import com.mondal.mondal_shop.request.CreateUserRequest;
import com.mondal.mondal_shop.request.UserUpdateRequest;

public interface UserService {

    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UserUpdateRequest request,Long userId);
    void deleteUser(Long userId);
}
