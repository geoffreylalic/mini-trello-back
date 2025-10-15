package com.geoffrey.mini_trello_back.task.exceptions;

public class ProfileNotRelatedToProjectException extends RuntimeException {
    public ProfileNotRelatedToProjectException() {
        super("The profile perform the action");
    }
}
