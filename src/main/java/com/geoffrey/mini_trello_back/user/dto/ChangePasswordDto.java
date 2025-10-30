package com.geoffrey.mini_trello_back.user.dto;

import jakarta.validation.constraints.NotBlank;

public record ChangePasswordDto(@NotBlank String oldPassword, @NotBlank String newPassword) {
}
