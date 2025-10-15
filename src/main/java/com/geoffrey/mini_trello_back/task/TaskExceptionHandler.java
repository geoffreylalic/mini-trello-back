package com.geoffrey.mini_trello_back.task;

import com.geoffrey.mini_trello_back.common.ApiError;
import com.geoffrey.mini_trello_back.task.exceptions.ProfileNotRelatedToProjectException;
import com.geoffrey.mini_trello_back.task.exceptions.TaskNotFoundException;
import com.geoffrey.mini_trello_back.task.exceptions.TaskStatusMismatchException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TaskExceptionHandler {
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ApiError> handleTaskNotFoundError(TaskNotFoundException ex, HttpServletRequest request) {
        ApiError error = new ApiError(
                HttpStatus.NOT_FOUND.value(),
                "Task not found.",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(TaskStatusMismatchException.class)
    public ResponseEntity<ApiError> handleTaskNotFoundError(TaskStatusMismatchException ex, HttpServletRequest request) {
        ApiError error = new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                "Task status mismatch.",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(ProfileNotRelatedToProjectException.class)
    public ResponseEntity<ApiError> handleCannotCreateTaskError(ProfileNotRelatedToProjectException ex, HttpServletRequest request) {
        ApiError error = new ApiError(
                HttpStatus.FORBIDDEN.value(),
                "Unable to create task.",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

}
