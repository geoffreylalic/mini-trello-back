package com.geoffrey.mini_trello_back.project;

import com.geoffrey.mini_trello_back.common.ApiError;
import com.geoffrey.mini_trello_back.project.exceptions.ProjecNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProjectExceptionHandler {

    @ExceptionHandler(ProjecNotFoundException.class)
    public ResponseEntity<ApiError> handleProjecNotFoundError(ProjecNotFoundException ex, HttpServletRequest request) {
        ApiError error = new ApiError(HttpStatus.NOT_FOUND.value(), "Project not found.", ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
