package com.geoffrey.mini_trello_back.role.exceptions;

public class RoleNameNotFound extends RuntimeException {
    public RoleNameNotFound(String roleName) {
        super("Role name:" + roleName + " not found.");
    }
}
