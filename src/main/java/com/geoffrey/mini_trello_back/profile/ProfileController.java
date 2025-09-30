package com.geoffrey.mini_trello_back.profile;

import com.geoffrey.mini_trello_back.profile.dto.CreateProfileDto;
import com.geoffrey.mini_trello_back.profile.dto.PatchProfileDto;
import com.geoffrey.mini_trello_back.profile.dto.ProfileResponseDto;
import com.geoffrey.mini_trello_back.project.dto.SimpleProjectDto;
import com.geoffrey.mini_trello_back.task.dto.ProfileTasksDto;
import com.geoffrey.mini_trello_back.task.dto.SimpleTaskResponseDto;
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

    @PatchMapping("profiles/{profile_id}")
    public ProfileResponseDto patchProfile(@Valid @RequestBody PatchProfileDto profileDto, @PathVariable("profile_id") int profileId) {
        return profileService.patchProfile(profileDto, profileId);
    }

    @GetMapping("profiles/{profile_id}/projects")
    public List<SimpleProjectDto> getProjectsProfile(@PathVariable("profile_id") int profileId) {
        return profileService.getProjectsProfile(profileId);
    }

    @GetMapping("profiles/{profile_id}/tasks")
    public List<ProfileTasksDto> getTasksProfiles(@PathVariable("profile_id") int profileId) {
        return profileService.getTasksProfiles(profileId);
    }
}
