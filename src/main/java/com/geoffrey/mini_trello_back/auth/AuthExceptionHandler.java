package com.geoffrey.mini_trello_back.auth;

import com.geoffrey.mini_trello_back.auth.exceptions.PasswordsMissmatchException;
import com.geoffrey.mini_trello_back.common.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice()
public class AuthExceptionHandler {
    @ExceptionHandler(PasswordsMissmatchException.class)
    public ResponseEntity<ApiError> handlePasswordsMissmatchError(PasswordsMissmatchException ex,
                                                                  HttpServletRequest request) {
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST.value(), "Passwords missmatch.", ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}

