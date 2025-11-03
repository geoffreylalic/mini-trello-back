package com.geoffrey.mini_trello_back.user;

import com.geoffrey.mini_trello_back.auth.AuthUtils;
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
        String roleName = userDto.role();
        Role role = roleRepository.findByName(roleName).orElseThrow(() -> new RoleNameNotFound(roleName));
        int nbUserEmail = userRepository.countUsersByEmail(userDto.email());
        if (nbUserEmail > 0) {
            throw new UserEmailExistsException(userDto.email());
        }

        User inputUser = userMapper.toUser(userDto, role);
        String encodedPassword = passwordEncoder.encode(userDto.password());
        inputUser.setPassword(encodedPassword);
        User newUser = userRepository.save(inputUser);
        return userMapper.toUserResponse(newUser);
    }

    @Override
    public ResponsePaginatedDto<List<UserResponseDto>> listUsers(Pageable pageable, String email) {
        Page<UserResponseDto> page = userRepository.findByEmailContaining(pageable, email).map(userMapper::toUserResponse);
        return responsePaginatedMapper.toResponsePaginatedDto(page, pageable);
    }

    @Override
    public UserResponseDto getUserById(int userId, User authUser) {

        User user = userRepository.findUserById(userId)
                .orElseThrow(() -> new UserDoesNotExistsException(userId));

        AuthUtils.checkAccessResource(user, authUser);

        return userMapper.toUserResponse(user);
    }


    @Override
    public UserResponseDto patchUserById(int userId, UpdateUserDto userDto, User authUser) {
        User user = userRepository.findUserById(userId).orElseThrow(() -> new UserDoesNotExistsException(userId));
        AuthUtils.checkAccessResource(user, authUser);
        if (!userDto.firstName().isEmpty()) {
            user.setFirstName(userDto.firstName());
        }
        if (!userDto.lastName().isEmpty()) {
            user.setLastName(userDto.lastName());
        }
        if (!userDto.email().isEmpty() && !user.getEmail().equals(userDto.email())) {
            int nbUserEmail = userRepository.countUsersByEmail(userDto.email());
            if (nbUserEmail > 0) {
                throw new UserEmailExistsException(userDto.email());
            }
            user.setEmail(userDto.email());
        }
        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    @Override
    public void deleteUserById(int userId, User authUser) {
        User user = userRepository.findUserById(userId).orElseThrow(() -> new UserDoesNotExistsException(userId));
        AuthUtils.checkAccessResource(user, authUser);
        userRepository.delete(user);
    }

    @Override
    public UserResponseDto updateUserRole(int userId, UserRoleDto userRoleDto, User authUser) {
        String roleName = userRoleDto.role();
        User user = userRepository.findUserById(userId).orElseThrow(() -> new UserDoesNotExistsException(userId));
        AuthUtils.checkAccessResource(user, authUser);
        Role role = roleRepository.findByName(roleName).orElseThrow(() -> new RoleNameNotFound(roleName));
        user.setRole(role);
        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponseDto changePassword(int userId, ChangePasswordDto passwordDto, User authUser) {
        User user = userRepository.findUserById(userId).orElseThrow(() -> new UserDoesNotExistsException(userId));
        AuthUtils.checkAccessResource(user, authUser);
        boolean matches = passwordEncoder.matches(passwordDto.oldPassword(), user.getPassword());
        if (!matches) {
            throw new UserPasswordInvalid();
        }

        String encoded = passwordEncoder.encode(passwordDto.newPassword());
        user.setPassword(encoded);

        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findUserByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found."));
    }

}
