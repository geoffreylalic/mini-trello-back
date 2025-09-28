package com.geoffrey.mini_trello_back.profile;

import com.geoffrey.mini_trello_back.profile.dto.CreateProfileDto;
import com.geoffrey.mini_trello_back.profile.dto.ProfileResponseDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("profiles")
    public List<ProfileResponseDto> listProfiles() {
        return profileService.listProfiles();
    }

    @GetMapping("profiles/{profile_id}")
    public ProfileResponseDto getProfile(@PathVariable("profile_id") int profileId) {
        return profileService.getProfile(profileId);
    }
}
