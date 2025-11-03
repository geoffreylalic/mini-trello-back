package com.geoffrey.mini_trello_back.task.dto;

import jakarta.validation.constraints.Email;

public record UpdateAssignedTaskDto(@Email String email) {
}
