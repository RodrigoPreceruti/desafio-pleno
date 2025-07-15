package com.example.demo.exception;

public record ExceptionResponseDTO(
        Integer statusCode,
        String message
) {
}
