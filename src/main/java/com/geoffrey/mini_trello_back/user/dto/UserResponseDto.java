package com.geoffrey.mini_trello_back.user.dto;

import com.geoffrey.mini_trello_back.role.dto.RoleDto;

public record UserResponseDto(
        Integer id,
        String email,
        String firstName,
        String lastName,
        String role
) {
}
