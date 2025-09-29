package com.geoffrey.mini_trello_back.profile;


import com.geoffrey.mini_trello_back.common.ApiError;
import com.geoffrey.mini_trello_back.profile.exceptions.ProfileNotFoundException;
import com.geoffrey.mini_trello_back.profile.exceptions.ProfileUserAlreadyExistsException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice()
public class ProfileExceptionHandler {

    @ExceptionHandler(ProfileUserAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleProfileUserAlreadyExistsError(ProfileUserAlreadyExistsException ex, HttpServletRequest request) {
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST.value(), "Profile already exists.", ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);

    }

    @ExceptionHandler(ProfileNotFoundException.class)
    public ResponseEntity<ApiError> handleProfileNotFoundError(ProfileNotFoundException ex, HttpServletRequest request) {
        ApiError error = new ApiError(HttpStatus.NOT_FOUND.value(), "Profile not found.", ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
