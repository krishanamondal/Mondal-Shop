package com.mondal.mondal_shop.service.user;

import com.mondal.mondal_shop.dto.UserDto;
import com.mondal.mondal_shop.exception.AlreadyExistsException;
import com.mondal.mondal_shop.exception.ResourceNotFoundException;
import com.mondal.mondal_shop.model.User;
import com.mondal.mondal_shop.repository.UserRepository;
import com.mondal.mondal_shop.request.CreateUserRequest;
import com.mondal.mondal_shop.request.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found with this id "+userId));

    }

    @Override
    public User createUser(CreateUserRequest request) {

        return Optional.of(request).filter(user -> !userRepository.existsByEmail(request.getEmail()))
                .map(req -> {
                    User user = new User();
                    user.setEmail(request.getEmail());
                    user.setPassword(request.getPassword());
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    return userRepository.save(user);

                }).orElseThrow(() -> new AlreadyExistsException("Oops! "+request.getEmail()+" already exists"));
    }

    @Override
    public User updateUser(UserUpdateRequest request,Long userId) {
        return userRepository.findById(userId).map(existingUser ->{
            existingUser.setFirstName(request.getFirstName());
            existingUser.setLastName(request.getLastName());
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new ResourceNotFoundException("user not found"));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId)
                .ifPresentOrElse(userRepository::delete, () -> {
            throw new ResourceNotFoundException("User not found");
        });

    }
    @Override
    public UserDto convertUserToDto(User user){
        return modelMapper.map(user, UserDto.class);
    }
}
//5:55