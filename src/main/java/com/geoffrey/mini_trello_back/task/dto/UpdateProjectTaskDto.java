package com.geoffrey.mini_trello_back.task.dto;

import jakarta.validation.constraints.NotNull;

public record UpdateProjectTaskDto(
        @NotNull Integer projectId
) {
}
