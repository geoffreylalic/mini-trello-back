package com.geoffrey.mini_trello_back.profile.exceptions;

public class ProfileNotFoundException extends RuntimeException {
    public ProfileNotFoundException(Integer profileId) {
        super("The profile: " + String.valueOf(profileId) + " does not exists.");
    }

    public ProfileNotFoundException() {
        super("Profile does not exists.");
    }
}
