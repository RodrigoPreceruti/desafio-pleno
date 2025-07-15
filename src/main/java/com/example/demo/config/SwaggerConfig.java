package com.example.demo.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi incidentsApi() {
        return GroupedOpenApi.builder()
                .group("API")
                .packagesToScan("com.example.demo.controller")
                .build();
    }
}
