package com.geoffrey.mini_trello_back.user.dto;

import jakarta.validation.constraints.Email;

public record UpdateUserDto(
        @Email String email,
        String password) {
}
