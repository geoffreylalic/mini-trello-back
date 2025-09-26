package com.geoffrey.mini_trello_back.profile.exceptions;

public class ProfileUserAlreadyExistsException extends RuntimeException {
    public ProfileUserAlreadyExistsException(Integer userId) {
        super("The user with the id: " + String.valueOf(userId) + ", already has a profile");
    }
}
