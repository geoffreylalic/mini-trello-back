package com.geoffrey.mini_trello_back.user;

import com.geoffrey.mini_trello_back.user.dto.CreateUserDto;
import com.geoffrey.mini_trello_back.user.dto.UpdateUserDto;
import com.geoffrey.mini_trello_back.user.dto.UserResponseDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public UserResponseDto createUser(@Valid @RequestBody CreateUserDto userDto) {
        return userService.createUser(userDto);
    }

    @GetMapping("/users")
    public List<UserResponseDto> listUsers() {
        return userService.listUsers();
    }

    @GetMapping("/users/{id}")
    public UserResponseDto getUserById(@PathVariable("id") int userId) {

        return userService.getUserById(userId);
    }

    @PatchMapping("/users/{id}")
    public UserResponseDto patchUserById(@PathVariable("id") int userId, @Valid @RequestBody UpdateUserDto userDto) {
        return userService.patchUserById(userId, userDto);
    }

}
