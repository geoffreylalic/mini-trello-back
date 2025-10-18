package com.geoffrey.mini_trello_back.auth;

import com.geoffrey.mini_trello_back.auth.dto.*;
import com.geoffrey.mini_trello_back.user.User;
import com.geoffrey.mini_trello_back.user.dto.UserResponseDto;

public interface AuthenticationService {
    UserResponseDto register(RegisterDto registerDto);

    AuthResponseDto login(LoginDto loginDto);

    void logout();

    AuthResponseDto refresh(RefreshTokenDto refreshTokenDto);

    MeResponseDto getMe(User currentUser);
}
