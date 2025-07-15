package com.example.demo.dto;

import com.example.demo.entity.UserRole;

public record CreateUserDTO(
        String login,
        String password,
        UserRole role
) {
}
