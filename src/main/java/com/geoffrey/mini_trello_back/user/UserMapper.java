package com.geoffrey.mini_trello_back.user;

import com.geoffrey.mini_trello_back.user.dto.CreateUserDto;
import com.geoffrey.mini_trello_back.user.dto.UpdateUserDto;
import com.geoffrey.mini_trello_back.user.dto.UserResponseDto;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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
        return new UserResponseDto(user.getId(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getRole().getName());
    }
}
