package com.geoffrey.mini_trello_back.task.dto;

import jakarta.validation.constraints.NotNull;

public record CreateTaskDto(
        @NotNull String title,
        String description,
        String status,
        Integer profileId,
        @NotNull Integer projectId
) {
}
