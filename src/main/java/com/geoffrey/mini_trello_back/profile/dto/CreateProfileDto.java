package com.geoffrey.mini_trello_back.profile.dto;

import com.geoffrey.mini_trello_back.profile.Role;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record CreateProfileDto(
        @NotNull
        Integer userId,
        @Past @NotNull
        LocalDate dateOfBirth,
        @NotNull
        Role role) {
}
