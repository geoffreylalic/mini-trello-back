package com.geoffrey.mini_trello_back.role.exceptions;

public class RoleAlreadyExistsException extends RuntimeException {
    public RoleAlreadyExistsException(String name) {
        super("Role with the name:" + name + " already exists.");
    }
}
