package com.example.demo.config;

import com.example.demo.entity.User;
import com.example.demo.entity.UserRole;
import com.example.demo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class UserInitializer {
    private final UserRepository repository;

    public UserInitializer(UserRepository repository) {
        this.repository = repository;
    }

    @Bean
    public CommandLineRunner initAdminUser(UserRepository userRepository) {
        return args -> {
            if (this.repository.findOptionalByLogin("admin").isEmpty()) {
                User user = new User();
                user.setLogin("admin");
                user.setPassword(new BCryptPasswordEncoder().encode("123456"));
                user.setRole(UserRole.ADMIN);

                userRepository.save(user);
            }
        };
    }
}
