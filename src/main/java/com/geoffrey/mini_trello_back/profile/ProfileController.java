package com.geoffrey.mini_trello_back.profile;

import com.geoffrey.mini_trello_back.common.ResponsePaginatedDto;
import com.geoffrey.mini_trello_back.profile.dto.CreateProfileDto;
import com.geoffrey.mini_trello_back.profile.dto.PatchProfileDto;
import com.geoffrey.mini_trello_back.profile.dto.ProfileResponseDto;
import com.geoffrey.mini_trello_back.project.dto.SimpleProjectDto;
import com.geoffrey.mini_trello_back.task.dto.ProfileTasksDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/profiles/")
public class ProfileController {
    ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping("")
    public ProfileResponseDto createProfile(@Valid @RequestBody CreateProfileDto profileDto) {
        return profileService.createProfile(profileDto);
    }

    @GetMapping("")
    public ResponsePaginatedDto<List<ProfileResponseDto>> listProfiles(@PageableDefault Pageable pageable) {
        return profileService.listProfiles(pageable);
    }

    @GetMapping("{profile_id}")
    public ProfileResponseDto getProfile(@PathVariable("profile_id") int profileId) {
        return profileService.getProfile(profileId);
    }

    @PatchMapping("{profile_id}")
    public ProfileResponseDto patchProfile(@Valid @RequestBody PatchProfileDto profileDto, @PathVariable("profile_id") int profileId) {
        return profileService.patchProfile(profileDto, profileId);
    }

    @GetMapping("{profile_id}/projects")
    public ResponsePaginatedDto<List<SimpleProjectDto>> getProjectsProfile(@PathVariable("profile_id") int profileId, @PageableDefault Pageable pageable) {
        return profileService.getProjectsProfile(profileId, pageable);
    }

    @GetMapping("{profile_id}/tasks")
    public ResponsePaginatedDto<List<ProfileTasksDto>> getTasksProfiles(@PathVariable("profile_id") int profileId, @PageableDefault Pageable pageable) {
        return profileService.getTasksProfiles(profileId, pageable);
    }
}
