package com.geoffrey.mini_trello_back.user.exceptions;

public class UserEmailExistsException extends RuntimeException {
    public UserEmailExistsException(String email) {
        super("The user with the " + email + " already exists.");
    }
}
