package com.geoffrey.mini_trello_back.user;

import com.geoffrey.mini_trello_back.user.dto.CreateUserDto;
import com.geoffrey.mini_trello_back.user.dto.UpdateUserDto;
import com.geoffrey.mini_trello_back.user.dto.UserResponseDto;
import com.geoffrey.mini_trello_back.user.exceptions.UserDoesNotExistsException;
import com.geoffrey.mini_trello_back.user.exceptions.UserEmailExistsException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserResponseDto createUser(CreateUserDto userDto) {
        int nbUserEmail = userRepository.countUsersByEmail(userDto.email());
        if (nbUserEmail > 0) {
            throw new UserEmailExistsException(userDto.email());
        }
        User inputUser = userMapper.toUser(userDto);
        User newUser = userRepository.save(inputUser);
        return userMapper.toUserResponse(newUser);
    }

    public List<UserResponseDto> listUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.toListUserResponse(users);
    }

    public UserResponseDto getUserById(int userId) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new UserDoesNotExistsException(userId);
        }
        return userMapper.toUserResponse(user);
    }

    public UserResponseDto patchUserById(int userId, UpdateUserDto userDto) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new UserDoesNotExistsException(userId);
        }
        if (userDto.email() != null) {
            user.setEmail(userDto.email());
        }
        if (userDto.password() != null) {
            user.setPassword(userDto.password());
        }
        userRepository.save(user);
        return userMapper.toUserResponse(user);

    }
}
