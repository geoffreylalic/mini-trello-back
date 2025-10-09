package com.geoffrey.mini_trello_back.user;

import com.geoffrey.mini_trello_back.common.ResponsePaginatedDto;
import com.geoffrey.mini_trello_back.user.dto.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("")
    public UserResponseDto createUser(@Valid @RequestBody CreateUserDto userDto) {
        return userService.createUser(userDto);
    }

    @GetMapping("")
    public ResponsePaginatedDto<List<UserResponseDto>> listUsers(@PageableDefault(size = 10, page = 0) Pageable pageable) {
        return userService.listUsers(pageable);

    }

    @GetMapping("/{id}")
    public UserResponseDto getUserById(@PathVariable("id") int userId) {

        return userService.getUserById(userId);
    }

    @PatchMapping("/{id}")
    public UserResponseDto patchUserById(@PathVariable("id") int userId, @Valid @RequestBody UpdateUserDto userDto) {
        return userService.patchUserById(userId, userDto);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable("id") int userId) {
        userService.deleteUserById(userId);
    }

    @PatchMapping("/{id}/role")
    public UserResponseDto updateUserRole(@PathVariable("id") int userId, @Valid @RequestBody UserRoleDto userRoleDto) {
        return userService.updateUserRole(userId, userRoleDto);
    }

    @PatchMapping("/{id}/password")
    public UserResponseDto changePassword(@PathVariable("id") int userId, ChangePasswordDto newPasswordDto) {
        return userService.changePassword(userId, newPasswordDto);
    }

}
