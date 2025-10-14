package com.geoffrey.mini_trello_back.auth;

import com.geoffrey.mini_trello_back.auth.dto.AuthResponseDto;
import com.geoffrey.mini_trello_back.auth.dto.LoginDto;
import com.geoffrey.mini_trello_back.auth.dto.RefreshTokenDto;
import com.geoffrey.mini_trello_back.auth.dto.RegisterDto;
import com.geoffrey.mini_trello_back.user.dto.UserResponseDto;

public interface AuthenticationService {
    UserResponseDto register(RegisterDto registerDto);

    AuthResponseDto login(LoginDto loginDto);

    void logout();

    AuthResponseDto refresh(RefreshTokenDto refreshTokenDto);
}
