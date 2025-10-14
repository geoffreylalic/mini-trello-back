package com.geoffrey.mini_trello_back.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record RegisterDto(
        @NotBlank String email,
        @NotBlank String password,
        @NotBlank String confirmPassword,
        @NotBlank String firstName,
        @NotBlank String lastName) {
}
