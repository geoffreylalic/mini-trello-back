package com.geoffrey.mini_trello_back.auth.dto;

import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Value;

public record RefreshTokenDto(@NotBlank @Value("refresh_token") String refreshToken) {
}
