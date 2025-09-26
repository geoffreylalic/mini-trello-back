package com.geoffrey.mini_trello_back.profile;

import com.geoffrey.mini_trello_back.profile.dto.CreateProfileDto;
import com.geoffrey.mini_trello_back.profile.dto.ProfileResponseDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {
    ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping("profiles")
    public ProfileResponseDto createProfile(@Valid @RequestBody CreateProfileDto profileDto) {
        return profileService.createProfile(profileDto);
    }

}
