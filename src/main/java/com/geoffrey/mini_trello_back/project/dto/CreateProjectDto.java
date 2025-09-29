package com.geoffrey.mini_trello_back.project.dto;

import jakarta.validation.constraints.NotNull;

public record CreateProjectDto(@NotNull String name,
                               String description,
                               @NotNull Integer profileId) {
}
