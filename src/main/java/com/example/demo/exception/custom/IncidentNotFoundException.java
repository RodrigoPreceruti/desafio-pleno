package com.example.demo.exception.custom;

public class IncidentNotFoundException extends RuntimeException {
  public IncidentNotFoundException(String message) {
    super(message);
  }
}
