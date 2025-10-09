package com.geoffrey.mini_trello_back.user.exceptions;

public class UserPasswordInvalid extends RuntimeException {
    public UserPasswordInvalid() {
        super("Invalid password");
    }
}
