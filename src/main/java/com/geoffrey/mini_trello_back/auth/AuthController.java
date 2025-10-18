package com.geoffrey.mini_trello_back.auth;

import com.geoffrey.mini_trello_back.auth.dto.*;
import com.geoffrey.mini_trello_back.user.User;
import com.geoffrey.mini_trello_back.user.dto.UserResponseDto;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/me")
    public MeResponseDto refresh(@AuthenticationPrincipal User currentUser) {
        return authenticationService.getMe(currentUser);
    }
}
