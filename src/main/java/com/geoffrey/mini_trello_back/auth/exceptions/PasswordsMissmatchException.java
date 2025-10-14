package com.geoffrey.mini_trello_back.auth.exceptions;

public class PasswordsMissmatchException extends RuntimeException {
    public PasswordsMissmatchException() {
        super("Passwords mismatch");
    }
}
