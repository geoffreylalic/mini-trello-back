package com.geoffrey.mini_trello_back.profile.dto;

import java.time.LocalDate;

public record ProfileDto(
        Integer id,
        LocalDate dateOfBirth
) {
}
