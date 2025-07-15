package com.example.demo.dto;

import com.example.demo.entity.User;
import com.example.demo.entity.UserRole;

import java.time.LocalDateTime;

public record UserEntityDTO(
        Long idUser,
        String login,
        UserRole role,
        LocalDateTime createdAt
) {
    public UserEntityDTO(User user) {
        this(
                user.getIdUser(),
                user.getLogin(),
                user.getRole(),
                user.getCreatedAt()
        );
    }
}
