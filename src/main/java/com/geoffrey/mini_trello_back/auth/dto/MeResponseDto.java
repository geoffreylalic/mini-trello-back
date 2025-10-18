package com.geoffrey.mini_trello_back.auth.dto;

import com.geoffrey.mini_trello_back.profile.dto.ProfileDto;
import com.geoffrey.mini_trello_back.user.dto.UserResponseDto;

public record MeResponseDto(UserResponseDto user, ProfileDto profile) {
}
