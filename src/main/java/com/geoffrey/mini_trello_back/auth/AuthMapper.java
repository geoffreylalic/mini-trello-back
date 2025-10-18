package com.geoffrey.mini_trello_back.auth;

import com.geoffrey.mini_trello_back.auth.dto.AuthResponseDto;
import com.geoffrey.mini_trello_back.auth.dto.MeResponseDto;
import com.geoffrey.mini_trello_back.profile.Profile;
import com.geoffrey.mini_trello_back.profile.ProfileMapper;
import com.geoffrey.mini_trello_back.user.User;
import com.geoffrey.mini_trello_back.user.UserMapper;
import com.geoffrey.mini_trello_back.user.dto.SimpleUserDto;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {
    private final UserMapper userMapper;
    private final ProfileMapper profileMapper;


    public AuthMapper(UserMapper userMapper, ProfileMapper profileMapper) {
        this.userMapper = userMapper;
        this.profileMapper = profileMapper;
    }


    AuthResponseDto toAuthResponseDto(User user, String accessToken, String refreshToken) {
        SimpleUserDto simpleUserDto = userMapper.toSimpleUserDto(user);
        return new AuthResponseDto(simpleUserDto, accessToken, refreshToken);
    }

    public MeResponseDto toMeResponseDto(User user, Profile profile) {
        if (profile == null) {
            return new MeResponseDto(userMapper.toUserResponse(user), null);
        }
        return new MeResponseDto(userMapper.toUserResponse(user), profileMapper.toProfileDto(profile));
    }
}
