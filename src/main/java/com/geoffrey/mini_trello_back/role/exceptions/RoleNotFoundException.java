package com.geoffrey.mini_trello_back.role.exceptions;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(int id) {
        super("Role with the id:" + id + " not found.");
    }
}
