package com.sudo.io;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class AuthResponse
{
    private String email;
    private String role;
    private String token;
}
