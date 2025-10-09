package com.geoffrey.mini_trello_back.user;

import com.geoffrey.mini_trello_back.common.ResponsePaginatedDto;
import com.geoffrey.mini_trello_back.common.ResponsePaginatedMapper;
import com.geoffrey.mini_trello_back.role.Role;
import com.geoffrey.mini_trello_back.role.RoleRepository;
import com.geoffrey.mini_trello_back.role.exceptions.RoleNameNotFound;
import com.geoffrey.mini_trello_back.user.dto.*;
import com.geoffrey.mini_trello_back.user.exceptions.UserDoesNotExistsException;
import com.geoffrey.mini_trello_back.user.exceptions.UserEmailExistsException;
import com.geoffrey.mini_trello_back.user.exceptions.UserPasswordInvalid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ResponsePaginatedMapper responsePaginatedMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, ResponsePaginatedMapper responsePaginatedMapper, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.responsePaginatedMapper = responsePaginatedMapper;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponseDto createUser(CreateUserDto userDto) {
        String role = userDto.role();
        boolean roleExits = roleRepository.existsByName(role);
        if (!roleExits) {
            throw new RoleNameNotFound(role);
        }
        int nbUserEmail = userRepository.countUsersByEmail(userDto.email());
        if (nbUserEmail > 0) {
            throw new UserEmailExistsException(userDto.email());
        }
        User inputUser = userMapper.toUser(userDto);
        User newUser = userRepository.save(inputUser);
        return userMapper.toUserResponse(newUser);
    }

    @Override
    public ResponsePaginatedDto<List<UserResponseDto>> listUsers(Pageable pageable) {
        Page<UserResponseDto> page = userRepository.findAll(pageable).map(userMapper::toUserResponse);
        return responsePaginatedMapper.toResponsePaginatedDto(page, pageable);
    }

    @Override
    public UserResponseDto getUserById(int userId) {
        return userRepository.findUserById(userId)
                .map(userMapper::toUserResponse)
                .orElseThrow(() -> new UserDoesNotExistsException(userId));
    }

    @Override
    public UserResponseDto patchUserById(int userId, UpdateUserDto userDto) {
        User user = userRepository.findUserById(userId).orElseThrow(() -> new UserDoesNotExistsException(userId));
        user.setEmail(userDto.email());
        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    @Override
    public void deleteUserById(int userId) {
        User user = userRepository.findUserById(userId).orElseThrow(() -> new UserDoesNotExistsException(userId));
        userRepository.delete(user);
    }

    @Override
    public UserResponseDto updateUserRole(int userId, UserRoleDto userRoleDto) {
        String roleName = userRoleDto.role();
        User user = userRepository.findUserById(userId).orElseThrow(() -> new UserDoesNotExistsException(userId));
        Role role = roleRepository.findByName(roleName).orElseThrow(() -> new RoleNameNotFound(roleName));
        user.setRole(role);
        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponseDto changePassword(int userId, ChangePasswordDto passwordDto) {
        User user = userRepository.findUserById(userId).orElseThrow(() -> new UserDoesNotExistsException(userId));
        boolean matches = passwordEncoder.matches(passwordEncoder.encode(passwordDto.newPassword()), user.getPassword());
        if (matches) {
            throw new UserPasswordInvalid();
        }

        String encoded = passwordEncoder.encode(passwordDto.newPassword());
        user.setPassword(encoded);

        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
