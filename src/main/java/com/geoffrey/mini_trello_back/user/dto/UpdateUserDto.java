package com.geoffrey.mini_trello_back.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UpdateUserDto(
        @NotBlank @Email String email,
        @NotBlank String firstName,
        @NotBlank String lastName
) {
}
