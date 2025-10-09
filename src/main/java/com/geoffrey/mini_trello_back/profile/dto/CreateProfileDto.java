package com.geoffrey.mini_trello_back.profile.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record CreateProfileDto(
        @NotNull
        Integer userId,
        @Past @NotNull
        LocalDate dateOfBirth) {
}
