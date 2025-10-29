package com.geoffrey.mini_trello_back.user;

import com.geoffrey.mini_trello_back.common.ResponsePaginatedDto;
import com.geoffrey.mini_trello_back.user.dto.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/")
public class UserController {

    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    // todo: move to /admin api
    @PostMapping("")
    public UserResponseDto createUser(@Valid @RequestBody CreateUserDto userDto) {
        return userService.createUser(userDto);
    }

    // todo: move to /admin api
    @GetMapping("")
    public ResponsePaginatedDto<List<UserResponseDto>> listUsers(@PageableDefault(size = 10, page = 0) Pageable pageable) {
        return userService.listUsers(pageable);

    }

    @GetMapping("{id}")
    public UserResponseDto getUserById(@PathVariable("id") int userId, @AuthenticationPrincipal User authUser) {
        return userService.getUserById(userId, authUser);
    }

    @PatchMapping("{id}")
    public UserResponseDto patchUserById(@PathVariable("id") int userId, @Valid @RequestBody UpdateUserDto userDto, @AuthenticationPrincipal User authUser) {
        return userService.patchUserById(userId, userDto, authUser);
    }

    @DeleteMapping("{id}")
    public void deleteUserById(@PathVariable("id") int userId, @AuthenticationPrincipal User authUser) {
        userService.deleteUserById(userId, authUser);
    }

    @PatchMapping("{id}/role")
    public UserResponseDto updateUserRole(@PathVariable("id") int userId, @Valid @RequestBody UserRoleDto userRoleDto, @AuthenticationPrincipal User authUser) {
        return userService.updateUserRole(userId, userRoleDto, authUser);
    }

    @PatchMapping("{id}/password")
    public UserResponseDto changePassword(@PathVariable("id") int userId, ChangePasswordDto newPasswordDto, @AuthenticationPrincipal User authUser) {
        return userService.changePassword(userId, newPasswordDto, authUser);
    }

}
