package com.geoffrey.mini_trello_back.user;

import com.geoffrey.mini_trello_back.user.dto.CreateUserDto;
import com.geoffrey.mini_trello_back.user.dto.UserResponseDto;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserResponseDto createUser(CreateUserDto userDto) {
        User inputUser = userMapper.toUser(userDto);
        User newUser = userRepository.save(inputUser);
        return userMapper.toUserResponse(newUser);
    }
}
