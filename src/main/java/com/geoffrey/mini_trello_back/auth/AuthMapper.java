package com.geoffrey.mini_trello_back.auth;

import com.geoffrey.mini_trello_back.auth.dto.AuthResponseDto;
import com.geoffrey.mini_trello_back.user.User;
import com.geoffrey.mini_trello_back.user.UserMapper;
import com.geoffrey.mini_trello_back.user.dto.SimpleUserDto;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {
    public AuthMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    private final UserMapper userMapper;

    AuthResponseDto toAuthResponseDto(User user, String accessToken, String refreshToken) {
        SimpleUserDto simpleUserDto = userMapper.toSimpleUserDto(user);
        return new AuthResponseDto(simpleUserDto, accessToken, refreshToken);
    }
}
