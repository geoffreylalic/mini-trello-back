package com.geoffrey.mini_trello_back.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.geoffrey.mini_trello_back.user.dto.SimpleUserDto;

public record AuthResponseDto(SimpleUserDto user, @JsonProperty("access_token") String accesToken,
                              @JsonProperty("refresh_token") String refreshToken) {
}
