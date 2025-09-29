package com.geoffrey.mini_trello_back.profile.dto;

import com.geoffrey.mini_trello_back.user.dto.UserResponseDto;

import java.time.LocalDate;

public record SimpleProfileResponseDto(Integer id,
                                       UserResponseDto user,
                                       LocalDate dateOfBirth,
                                       String role) {
}
