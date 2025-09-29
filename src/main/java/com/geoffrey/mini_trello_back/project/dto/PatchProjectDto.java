package com.geoffrey.mini_trello_back.project.dto;

import jakarta.validation.constraints.NotNull;

public record PatchProjectDto(
        @NotNull String name,
        String description
) {
}
