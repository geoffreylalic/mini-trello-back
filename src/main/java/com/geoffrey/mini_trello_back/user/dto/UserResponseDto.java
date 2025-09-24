package com.geoffrey.mini_trello_back.user.dto;

public record UserResponseDto(
        Integer id,
        String email,
        String firstName,
        String lastName
) {
}
