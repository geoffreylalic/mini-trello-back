package com.geoffrey.mini_trello_back.profile.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record CreateProfileDto(
        @Past @NotNull
        LocalDate dateOfBirth) {
}
