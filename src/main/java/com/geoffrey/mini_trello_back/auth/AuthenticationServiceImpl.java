package com.geoffrey.mini_trello_back.auth;

import com.geoffrey.mini_trello_back.auth.dto.*;
import com.geoffrey.mini_trello_back.auth.exceptions.PasswordsMissmatchException;
import com.geoffrey.mini_trello_back.profile.Profile;
import com.geoffrey.mini_trello_back.profile.ProfileRepository;
import com.geoffrey.mini_trello_back.role.Role;
import com.geoffrey.mini_trello_back.role.RoleRepository;
import com.geoffrey.mini_trello_back.role.exceptions.RoleNameNotFound;
import com.geoffrey.mini_trello_back.security.JwtService;
import com.geoffrey.mini_trello_back.user.User;
import com.geoffrey.mini_trello_back.user.UserMapper;
import com.geoffrey.mini_trello_back.user.UserRepository;
import com.geoffrey.mini_trello_back.user.dto.UserResponseDto;
import com.geoffrey.mini_trello_back.user.exceptions.UserEmailExistsException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final AuthMapper authMapper;
    private final PasswordEncoder passwordEncoder;
    private final ProfileRepository profileRepository;

    public AuthenticationServiceImpl(UserRepository userRepository, UserMapper userMapper, JwtService jwtService, AuthenticationManager authenticationManager, RoleRepository roleRepository, AuthMapper authMapper, PasswordEncoder passwordEncoder, ProfileRepository profileRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.roleRepository = roleRepository;
        this.authMapper = authMapper;
        this.passwordEncoder = passwordEncoder;
        this.profileRepository = profileRepository;
    }

    @Override
    public AuthResponseDto login(LoginDto loginDto) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.email(), loginDto.password())
        );

        User user = (User) auth.getPrincipal();

        String accessToken = jwtService.generateAccessToken(user.getUsername());
        String refreshToken = jwtService.generateRefreshToken(user.getUsername());

        return authMapper.toAuthResponseDto(user, accessToken, refreshToken);
    }

    @Override
    @Transactional
    public UserResponseDto register(RegisterDto registerDto) {
        int nbUserEmail = userRepository.countUsersByEmail(registerDto.email());
        if (nbUserEmail > 0) {
            throw new UserEmailExistsException(registerDto.email());
        }
        checkPasswords(registerDto.password(), registerDto.confirmPassword());
        User inputUser = userMapper.toUser(registerDto);

        String roleName = "ROLE_USER";
        Role role = roleRepository.findByName(roleName).orElseThrow(() -> new RoleNameNotFound(roleName));
        inputUser.setRole(role);

        String encodedPassword = passwordEncoder.encode(registerDto.password());
        inputUser.setPassword(encodedPassword);


        User newUser = userRepository.save(inputUser);
        return userMapper.toUserResponse(newUser);
    }


    @Override
    public void logout() {

    }

    @Override
    public AuthResponseDto refresh(RefreshTokenDto refreshTokenDto) {
        String accessToken = jwtService.refreshAccessToken(refreshTokenDto.refreshToken());
        String username = jwtService.extractUsername(accessToken);
        String newRefreshToken = jwtService.generateRefreshToken(username);
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return authMapper.toAuthResponseDto(user, accessToken, newRefreshToken);
    }

    @Override
    public MeResponseDto getMe(User currentUser) {
        Profile profile = profileRepository.findByUserId(currentUser.getId()).orElse(null);
        return authMapper.toMeResponseDto(currentUser, profile);
    }

    private void checkPasswords(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new PasswordsMissmatchException();
        }
    }
}
