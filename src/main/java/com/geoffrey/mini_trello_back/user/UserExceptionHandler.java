package com.geoffrey.mini_trello_back.user;

import com.geoffrey.mini_trello_back.common.ApiError;
import com.geoffrey.mini_trello_back.user.exceptions.UserEmailExistsException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.geoffrey.mini_trello_back.user")
public class UserExceptionHandler {
    @ExceptionHandler(UserEmailExistsException.class)
    public ResponseEntity<ApiError> handleUserEmailExistsError(UserEmailExistsException ex,
                                                               HttpServletRequest request) {
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST.value(), "User email already exists", ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}

