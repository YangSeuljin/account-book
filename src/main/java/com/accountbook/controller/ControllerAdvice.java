package com.accountbook.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> illegalStateExceptionHandle(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
