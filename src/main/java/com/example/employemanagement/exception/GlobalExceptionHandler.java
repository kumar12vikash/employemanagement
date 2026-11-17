package com.example.employemanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

    @RestControllerAdvice
    public class GlobalExceptionHandler {

        // Employee not found
        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<Map<String, String>> handleNotFound(
                ResourceNotFoundException exception) {

            Map<String, String> error = new HashMap<>();

            error.put("message", exception.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(error);
        }

        // Duplicate email or other business conflict
        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<Map<String, String>> handleBadRequest(
                IllegalArgumentException exception) {

            Map<String, String> error = new HashMap<>();

            error.put("message", exception.getMessage());

            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(error);
        }

        // Validation errors
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<Map<String, String>> handleValidation(
                MethodArgumentNotValidException exception) {

            Map<String, String> errors = new HashMap<>();

            exception.getBindingResult().getFieldErrors()
                    .forEach(error ->
                            errors.put(error.getField(), error.getDefaultMessage())
                    );

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(errors);
        }
    }

