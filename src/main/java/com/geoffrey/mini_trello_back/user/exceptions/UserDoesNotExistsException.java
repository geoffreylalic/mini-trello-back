package com.geoffrey.mini_trello_back.user.exceptions;

public class UserDoesNotExistsException extends RuntimeException {
    public UserDoesNotExistsException(int userId) {
        super("User does not exists for the id: " + String.valueOf(userId));
    }
}
