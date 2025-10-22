package com.geoffrey.mini_trello_back.task.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public record CreateTaskDto(
        @NotBlank String title,
        String description,
        String status,
        @PositiveOrZero Integer projectId
) {
}
