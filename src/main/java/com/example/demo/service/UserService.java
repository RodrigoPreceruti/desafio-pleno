package com.example.demo.service;

import com.example.demo.dto.CreateUserDTO;
import com.example.demo.dto.UserEntityDTO;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {
    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntityDTO createUser(CreateUserDTO request) {
        User user = new User();
        user.setLogin(request.login());
        user.setRole(request.role());
        user.setCreatedAt(LocalDateTime.now());

        String passwordEncode = passwordEncoder.encode(request.password());

        user.setPassword(passwordEncode);

        this.repository.save(user);

        return new UserEntityDTO(user);
    }
}
