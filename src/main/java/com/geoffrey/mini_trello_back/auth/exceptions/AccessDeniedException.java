package com.geoffrey.mini_trello_back.auth.exceptions;

public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException() {
        super("The user cannot access this resource.");
    }
}
