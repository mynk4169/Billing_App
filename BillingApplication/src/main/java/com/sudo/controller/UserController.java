package com.sudo.controller;

import com.sudo.io.UserRequest;
import com.sudo.io.UserResponse;
import com.sudo.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class UserController
{
    private final UserServiceImpl userService;
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse registerUser(@RequestBody UserRequest request)
    {
        try{
            return userService.createUser(request);
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Unable to create user: "+e.getMessage());
        }
    }
    @GetMapping("/users")
    public List<UserResponse> readUsers()
    {
        return userService.readUsers();
    }
    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String id)
    {
        try
        {
            userService.deleteUser(id);
        }
        catch(Exception e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User not Found");
        }
    }
}
