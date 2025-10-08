package com.geoffrey.mini_trello_back.role.dto;

import jakarta.validation.constraints.NotBlank;

public record RoleDto(@NotBlank String name) {
}
