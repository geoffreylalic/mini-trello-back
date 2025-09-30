package com.geoffrey.mini_trello_back.task.exceptions;

import com.geoffrey.mini_trello_back.task.Status;

import java.util.Arrays;

public class TaskStatusMismatchException extends RuntimeException{
    public TaskStatusMismatchException(String status) {
        super("The given status:" + status +". status property needs to be in " + Arrays.stream(Status.values()).toList());
    }
}
