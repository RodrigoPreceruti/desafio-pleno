package com.example.demo.exception;

import com.example.demo.exception.custom.IncidentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdviceException {
    @ExceptionHandler(IncidentNotFoundException.class)
    public ResponseEntity<ExceptionResponseDTO> incidentNotFoundException(IncidentNotFoundException exception) {
        ExceptionResponseDTO response = new ExceptionResponseDTO(HttpStatus.NOT_FOUND.value(), exception.getMessage());

        return ResponseEntity.badRequest().body(response);
    }
}
