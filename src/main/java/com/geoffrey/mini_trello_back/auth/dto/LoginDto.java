package com.geoffrey.mini_trello_back.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginDto(@NotBlank String email, @NotBlank String password) {
}
