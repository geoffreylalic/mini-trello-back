package com.geoffrey.mini_trello_back.task.dto;

import jakarta.validation.constraints.NotNull;

public record UpdateAssignedTaskDto(@NotNull Integer profileId) {
}
