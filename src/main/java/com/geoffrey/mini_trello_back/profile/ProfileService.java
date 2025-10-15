package com.geoffrey.mini_trello_back.profile;

import com.geoffrey.mini_trello_back.auth.AuthUtils;
import com.geoffrey.mini_trello_back.auth.exceptions.AccessDeniedException;
import com.geoffrey.mini_trello_back.common.ResponsePaginatedDto;
import com.geoffrey.mini_trello_back.common.ResponsePaginatedMapper;
import com.geoffrey.mini_trello_back.profile.dto.CreateProfileDto;
import com.geoffrey.mini_trello_back.profile.dto.PatchProfileDto;
import com.geoffrey.mini_trello_back.profile.dto.ProfileResponseDto;
import com.geoffrey.mini_trello_back.profile.exceptions.ProfileNotFoundException;
import com.geoffrey.mini_trello_back.profile.exceptions.ProfileUserAlreadyExistsException;
import com.geoffrey.mini_trello_back.project.ProjectMapper;
import com.geoffrey.mini_trello_back.project.dto.SimpleProjectDto;
import com.geoffrey.mini_trello_back.task.TaskMapper;
import com.geoffrey.mini_trello_back.task.TaskRepository;
import com.geoffrey.mini_trello_back.task.dto.ProfileTasksDto;
import com.geoffrey.mini_trello_back.user.User;
import com.geoffrey.mini_trello_back.user.UserRepository;
import com.geoffrey.mini_trello_back.user.exceptions.UserDoesNotExistsException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;
    private final UserRepository userRepository;
    private final ProjectMapper projectMapper;
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final ResponsePaginatedMapper responsePaginatedMapper;

    public ProfileService(ProfileRepository profileRepository, UserRepository userRepository, ProfileMapper profileMapper, ProjectMapper projectMapper, TaskRepository taskRepository, TaskMapper taskMapper, ResponsePaginatedMapper responsePaginatedMapper) {
        this.profileRepository = profileRepository;
        this.profileMapper = profileMapper;
        this.userRepository = userRepository;
        this.projectMapper = projectMapper;
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.responsePaginatedMapper = responsePaginatedMapper;
    }

    public ProfileResponseDto createProfile(CreateProfileDto profileDto, User currentUser) {
        Integer userId = profileDto.userId();
        User userRequested = userRepository.findUserById(userId)
                .orElseThrow(() -> new UserDoesNotExistsException(userId));
        AuthUtils.checkAccessResource(userRequested, currentUser);
        long nbProfiles = profileRepository.countByUser_Id(userId);
        if (nbProfiles > 0) {
            throw new ProfileUserAlreadyExistsException(userId);
        }
        Profile newProfile = profileMapper.userToProfile(userRequested, profileDto.dateOfBirth());
        Profile profile = profileRepository.save(newProfile);

        return profileMapper.toProfileResponseDto(profile);
    }

    public ResponsePaginatedDto<List<ProfileResponseDto>> listProfiles(Pageable pageable) {
        Page<ProfileResponseDto> page = profileRepository.findAll(pageable).map(profileMapper::toProfileResponseDto);
        return responsePaginatedMapper.toResponsePaginatedDto(page, pageable);
    }

    public ProfileResponseDto getProfile(int profileId, User currentUser) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new ProfileNotFoundException(profileId));
        AuthUtils.checkAccessResource(profile.getUser(), currentUser);
        return profileMapper.toProfileResponseDto(profile);
    }

    public ProfileResponseDto patchProfile(PatchProfileDto profileDto, int profileId, User currentUser) {
        Profile profile = profileRepository.findById(profileId).orElseThrow(() -> new ProfileNotFoundException(profileId));
        AuthUtils.checkAccessResource(profile.getUser(), currentUser);
        profile.setDateOfBirth(profileDto.dateOfBirth());
        Profile newProfile = profileRepository.save(profile);
        return profileMapper.toProfileResponseDto(newProfile);
    }

    public ResponsePaginatedDto<List<SimpleProjectDto>> getProjectsProfile(Integer profileId, Pageable pageable, User currentUser) {
        Profile profile = profileRepository.findById(profileId).orElseThrow(() -> new ProfileNotFoundException(profileId));
        AuthUtils.checkAccessResource(profile.getUser(), currentUser);

        Page<SimpleProjectDto> page = profileRepository.findRelatedProjects(profileId, pageable).map(projectMapper::toSimpleProjectDto);
        return responsePaginatedMapper.toResponsePaginatedDto(page, pageable);
    }

    public ResponsePaginatedDto<List<ProfileTasksDto>> getTasksProfiles(int profileId, Pageable pageable, User currentUser) {
        Profile profile = profileRepository.findById(profileId).orElseThrow(() -> new ProfileNotFoundException(profileId));
        AuthUtils.checkAccessResource(profile.getUser(), currentUser);
        Page<ProfileTasksDto> page = taskRepository.findTasksByAssignedToId(profileId, pageable).map(taskMapper::toProfileTasksDto);
        return responsePaginatedMapper.toResponsePaginatedDto(page, pageable);
    }
}
