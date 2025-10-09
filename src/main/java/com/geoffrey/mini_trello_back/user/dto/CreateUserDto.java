package com.geoffrey.mini_trello_back.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record CreateUserDto(
        @NotEmpty
        String email,
        String firstName,
        String lastName,
        @NotEmpty
        String password,
        @NotBlank
        String role
) {
}
