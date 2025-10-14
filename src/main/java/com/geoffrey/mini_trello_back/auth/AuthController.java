package com.geoffrey.mini_trello_back.auth;

import com.geoffrey.mini_trello_back.auth.dto.AuthResponseDto;
import com.geoffrey.mini_trello_back.auth.dto.LoginDto;
import com.geoffrey.mini_trello_back.auth.dto.RefreshTokenDto;
import com.geoffrey.mini_trello_back.auth.dto.RegisterDto;
import com.geoffrey.mini_trello_back.user.dto.UserResponseDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationServiceImpl authenticationService;

    public AuthController(AuthenticationServiceImpl authenticationService) {
        this.authenticationService = authenticationService;
    }


    @PostMapping("/login")
    public AuthResponseDto login(@RequestBody @Valid LoginDto loginDto) {
        return authenticationService.login(loginDto);
    }

    @PostMapping("/register")
    public UserResponseDto register(@RequestBody @Valid RegisterDto registerDto) {
        return authenticationService.register(registerDto);
    }

    @PostMapping("/refresh-token")
    public AuthResponseDto refresh(@RequestBody @Valid RefreshTokenDto refreshTokenDto) {
        return authenticationService.refresh(refreshTokenDto);
    }
}
