package com.geoffrey.mini_trello_back.user;

import com.geoffrey.mini_trello_back.auth.dto.RegisterDto;
import com.geoffrey.mini_trello_back.role.Role;
import com.geoffrey.mini_trello_back.user.dto.CreateUserDto;
import com.geoffrey.mini_trello_back.user.dto.SimpleUserDto;
import com.geoffrey.mini_trello_back.user.dto.UserResponseDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toUser(CreateUserDto userDto, Role role) {
        User user = new User();
        user.setFirstName(userDto.firstName());
        user.setLastName(userDto.lastName());
        user.setPassword(userDto.password());
        user.setEmail(userDto.email());
        user.setRole(role);
        return user;
    }

    public User toUser(RegisterDto userDto) {
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

    public SimpleUserDto toSimpleUserDto(User user) {
        return new SimpleUserDto(user.getId(), user.getEmail());
    }
}
