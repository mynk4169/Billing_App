package com.sudo.controller;

import com.sudo.io.AuthRequest;
import com.sudo.io.AuthResponse;
import com.sudo.service.UserService;
import com.sudo.service.impl.AppUserDetailsServiceImpl;
import com.sudo.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
 import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthController
{
    private final PasswordEncoder encoder;
    private final AuthenticationManager manager;
    private final AppUserDetailsServiceImpl userDetailsService;
    private final JwtUtils jwtUtils;
    private final UserService userService;
    @PostMapping("/encode")
    private String encodePassword(@RequestBody Map<String,String> request)
    {
        return encoder.encode(request.get("password"));
    }
    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request)throws Exception
    {
        authenticate(request.getEmail(),request.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        final String jwtToken = jwtUtils.generateToken(userDetails);
        String role = userService.getUserRole(request.getEmail());
        return new AuthResponse(request.getEmail(),role,jwtToken);
    }
    public void authenticate(String email,String password) throws Exception
    {
        try
        {
            manager.authenticate(new UsernamePasswordAuthenticationToken(email,password));
        }catch (DisabledException  de)
        {
            throw new Exception("User Disabled");
        }
        catch (BadCredentialsException Be)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Email ID or  password is incorrect");
        }
    }
}
