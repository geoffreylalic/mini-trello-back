package com.geoffrey.mini_trello_back.auth;

import com.geoffrey.mini_trello_back.auth.exceptions.AccessDeniedException;
import com.geoffrey.mini_trello_back.user.User;

import java.util.Objects;

public class AuthUtils {
    private AuthUtils() {

    }

    public static void checkAccessResource(User requestedUser, User authUser) {
        if (!Objects.equals(requestedUser.getId(), authUser.getId())) {
            throw new AccessDeniedException();
        }
    }
}
