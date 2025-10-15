package com.geoffrey.mini_trello_back.task.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateTaskDto(
        @NotBlank String title,
        String description,
        String status,
        @NotBlank Integer profileId,
        @NotBlank Integer projectId
) {
}
