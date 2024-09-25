package com.mondal.mondal_shop.service.user;

import com.mondal.mondal_shop.dto.AuthResponseDto;
import com.mondal.mondal_shop.dto.UserDto;
import com.mondal.mondal_shop.exception.ResourceNotFoundException;
import com.mondal.mondal_shop.model.User;
import com.mondal.mondal_shop.model.UserRole;
import com.mondal.mondal_shop.repository.UserRepository;
import com.mondal.mondal_shop.request.CreateUserRequest;
import com.mondal.mondal_shop.request.LoginRequest;
import com.mondal.mondal_shop.request.UserUpdateRequest;
import com.mondal.mondal_shop.service.auth.JwtService;
import com.mondal.mondal_shop.service.auth.RefreshTokenService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found with this id "+userId));

    }

    @Override
    public AuthResponseDto createUser(CreateUserRequest request) {
        User user = User.builder()
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


//        return Optional.of(request).filter(user -> !userRepository.existsByusername(request.getUsername()))
//                .map(req -> {
//                    User user = new User();
//                    user.setUsername(request.getUsername());
//                    user.setPassword(request.getPassword());
//                    user.setFirstName(request.getFirstName());
//                    user.setLastName(request.getLastName());
//                    user.setRole(UserRole.USER);
//                    return userRepository.save(user);
//
//                }).orElseThrow(() -> new AlreadyExistsException("Oops! "+request.getUsername()+" already exists"));
    }

    @Override
    public AuthResponseDto login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),
                request.getPassword()));
        User user = userRepository.findByusername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var accessToken = jwtService.generateToken(user);
        var refreshToken = refreshTokenService.createRefreshToken(request.getUsername());
        return AuthResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();
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
    public User makeAdmin(UserDto userDto) {


        return null;
    }

    @Override
    public UserDto convertUserToDto(User user){
        return modelMapper.map(user, UserDto.class);
    }
}
//5:55

