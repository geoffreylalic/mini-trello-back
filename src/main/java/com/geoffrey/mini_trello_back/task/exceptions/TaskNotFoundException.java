package com.geoffrey.mini_trello_back.task.exceptions;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(Integer taskId) {
        super("Task id:" + String.valueOf(taskId) + " not found.");
    }
}
