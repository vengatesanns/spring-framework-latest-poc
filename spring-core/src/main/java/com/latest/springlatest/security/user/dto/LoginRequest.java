package com.latest.springlatest.security.user.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
