package com.geoffrey.mini_trello_back.profile;

import com.geoffrey.mini_trello_back.common.ResponsePaginatedDto;
import com.geoffrey.mini_trello_back.profile.dto.CreateProfileDto;
import com.geoffrey.mini_trello_back.profile.dto.PatchProfileDto;
import com.geoffrey.mini_trello_back.profile.dto.ProfileResponseDto;
import com.geoffrey.mini_trello_back.project.dto.SimpleProjectDto;
import com.geoffrey.mini_trello_back.task.dto.ProfileTasksDto;
import com.geoffrey.mini_trello_back.user.User;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ProfileResponseDto createProfile(@Valid @RequestBody CreateProfileDto profileDto,
                                            @AuthenticationPrincipal User currentUser) {
        return profileService.createProfile(profileDto, currentUser);
    }

    @GetMapping("")
    public ResponsePaginatedDto<List<ProfileResponseDto>> listProfiles(@PageableDefault Pageable pageable) {
        return profileService.listProfiles(pageable);
    }

    @GetMapping("{profile_id}")
    public ProfileResponseDto getProfile(@PathVariable("profile_id") int profileId,
                                         @AuthenticationPrincipal User currentUser) {
        return profileService.getProfile(profileId, currentUser);
    }

    @PatchMapping("{profile_id}")
    public ProfileResponseDto patchProfile(@Valid @RequestBody PatchProfileDto profileDto,
                                           @PathVariable("profile_id") int profileId,
                                           @AuthenticationPrincipal User currentUser) {
        return profileService.patchProfile(profileDto, profileId, currentUser);
    }

    @GetMapping("{profile_id}/projects")
    public ResponsePaginatedDto<List<SimpleProjectDto>> getProjectsProfile(@PathVariable("profile_id") int profileId,
                                                                           @PageableDefault Pageable pageable,
                                                                           @AuthenticationPrincipal User currentUser) {
        return profileService.getProjectsProfile(profileId, pageable, currentUser);
    }

    @GetMapping("{profile_id}/tasks")
    public ResponsePaginatedDto<List<ProfileTasksDto>> getTasksProfiles(@PathVariable("profile_id") int profileId,
                                                                        @PageableDefault Pageable pageable,
                                                                        @AuthenticationPrincipal User currentUser) {
        return profileService.getTasksProfiles(profileId, pageable, currentUser);
    }
}
