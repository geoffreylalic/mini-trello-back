package com.geoffrey.mini_trello_back.project.exceptions;

public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException(Integer projectId) {
        super("Project id:" + String.valueOf(projectId) + " not found.");
    }
}
