package com.sudo.service.impl;

import com.sudo.entity.UserEntity;
import com.sudo.io.UserRequest;
import com.sudo.io.UserResponse;
import com.sudo.repository.UserRepository;
import com.sudo.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService
{
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Override
    public UserResponse createUser(UserRequest request)
    {
        UserEntity newUser = convertToEntity(request);
        userRepository.save(newUser);
        return convertToResponse(newUser);

    }

    @Override
    public String getUserRole(String email)
    {
        UserEntity existingUser =userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found for the email"+email));
        return existingUser.getRole();
    }

    @Override
    public List<UserResponse> readUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(String id)
    {
       UserEntity existingUser= userRepository
                .findByUserId(id)
                .orElseThrow(()->new UsernameNotFoundException("user Not Found with Id"+id));
        userRepository.delete(existingUser);
    }
    private UserEntity convertToEntity(UserRequest request)
    {
       return UserEntity.builder()
                .userId(UUID.randomUUID().toString())
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .role(request.getRole().toUpperCase())
                .name(request.getName())
                .build();
    }
    private UserResponse convertToResponse(UserEntity userEntity)
    {
        return UserResponse.builder()
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .userId(userEntity.getUserId())
                .createdAt(userEntity.getCreatedAt())
                .updatedAt(userEntity.getUpdatedAt())
                .role(userEntity.getRole())
                .build();
    }

}
