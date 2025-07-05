package com.sudo.service;

import com.sudo.io.UserRequest;
import com.sudo.io.UserResponse;

import java.util.List;

public interface UserService
{
    UserResponse createUser(UserRequest request);
    String getUserRole(String email);
    List<UserResponse> readUsers();
    void deleteUser(String id);

}
