package com.geoffrey.mini_trello_back.user;

import com.geoffrey.mini_trello_back.common.ResponsePaginatedDto;
import com.geoffrey.mini_trello_back.common.ResponsePaginatedMapper;
import com.geoffrey.mini_trello_back.user.dto.CreateUserDto;
import com.geoffrey.mini_trello_back.user.dto.UpdateUserDto;
import com.geoffrey.mini_trello_back.user.dto.UserResponseDto;
import com.geoffrey.mini_trello_back.user.exceptions.UserDoesNotExistsException;
import com.geoffrey.mini_trello_back.user.exceptions.UserEmailExistsException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ResponsePaginatedMapper responsePaginatedMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper, ResponsePaginatedMapper responsePaginatedMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.responsePaginatedMapper = responsePaginatedMapper;
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

    public ResponsePaginatedDto<List<UserResponseDto>> listUsers(Pageable pageable) {
        Page<UserResponseDto> page = userRepository.findAll(pageable).map(userMapper::toUserResponse);
        return responsePaginatedMapper.toResponsePaginatedDto(page, pageable);
    }

    public UserResponseDto getUserById(int userId) {
        return userRepository.findUserById(userId)
                .map(userMapper::toUserResponse)
                .orElseThrow(() -> new UserDoesNotExistsException(userId));
    }

    public UserResponseDto patchUserById(int userId, UpdateUserDto userDto) {
        User user = userRepository.findUserById(userId).orElseThrow(() -> new UserDoesNotExistsException(userId));
        if (userDto.email() != null) {
            user.setEmail(userDto.email());
        }
        if (userDto.password() != null) {
            user.setPassword(userDto.password());
        }
        userRepository.save(user);
        return userMapper.toUserResponse(user);

    }

    public void deleteUserById(int userId) {
        User user = userRepository.findUserById(userId).orElseThrow(() -> new UserDoesNotExistsException(userId));
        userRepository.delete(user);
    }
}
