package com.geoffrey.mini_trello_back.user;

import com.geoffrey.mini_trello_back.user.dto.CreateUserDto;
import com.geoffrey.mini_trello_back.user.dto.UpdateUserDto;
import com.geoffrey.mini_trello_back.user.dto.UserResponseDto;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
public class UserMapper {
    public User toUser(CreateUserDto userDto) {
        User user = new User();
        user.setFirstName(userDto.firstName());
        user.setLastName(userDto.lastName());
        user.setPassword(userDto.password());
        user.setEmail(userDto.email());
        return user;
    }

    public UserResponseDto toUserResponse(User user) {
        return new UserResponseDto(user.getId(), user.getEmail(), user.getFirstName(), user.getLastName());
    }

    public List<UserResponseDto> toListUserResponse(List<User> users) {
        return users.stream().map(this::toUserResponse).toList();
    }

    public void mergeUser(User user, UpdateUserDto userDto) {
        if (StringUtils.hasLength(userDto.email())) {
            user.setEmail(userDto.email());
        }
        if (StringUtils.hasLength(userDto.password())) {
            user.setPassword(userDto.password());
        }
    }
}
