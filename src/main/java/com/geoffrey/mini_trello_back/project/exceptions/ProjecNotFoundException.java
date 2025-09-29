package com.geoffrey.mini_trello_back.project.exceptions;

public class ProjecNotFoundException extends RuntimeException {
    public ProjecNotFoundException(Integer projectId) {
        super("Project id:" + String.valueOf(projectId) + " not found.");
    }
}
