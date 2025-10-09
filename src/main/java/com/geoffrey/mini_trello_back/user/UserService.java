package com.geoffrey.mini_trello_back.user;

import com.geoffrey.mini_trello_back.common.ResponsePaginatedDto;
import com.geoffrey.mini_trello_back.user.dto.*;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService extends UserDetailsService {
    UserResponseDto createUser(CreateUserDto userDto);

    ResponsePaginatedDto<List<UserResponseDto>> listUsers(Pageable pageable);

    UserResponseDto getUserById(int userId);

    UserResponseDto patchUserById(int userId, UpdateUserDto userDto);

    void deleteUserById(int userId);

    UserResponseDto updateUserRole(int userId, UserRoleDto userRoleDto);

    UserResponseDto changePassword(int userId, ChangePasswordDto newPassword);
}
