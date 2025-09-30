package com.geoffrey.mini_trello_back.profile;

import com.geoffrey.mini_trello_back.profile.dto.CreateProfileDto;
import com.geoffrey.mini_trello_back.profile.dto.PatchProfileDto;
import com.geoffrey.mini_trello_back.profile.dto.ProfileResponseDto;
import com.geoffrey.mini_trello_back.profile.exceptions.ProfileNotFoundException;
import com.geoffrey.mini_trello_back.profile.exceptions.ProfileUserAlreadyExistsException;
import com.geoffrey.mini_trello_back.project.Project;
import com.geoffrey.mini_trello_back.project.ProjectMapper;
import com.geoffrey.mini_trello_back.project.dto.SimpleProjectDto;
import com.geoffrey.mini_trello_back.task.Task;
import com.geoffrey.mini_trello_back.task.TaskMapper;
import com.geoffrey.mini_trello_back.task.TaskRepository;
import com.geoffrey.mini_trello_back.task.dto.ProfileTasksDto;
import com.geoffrey.mini_trello_back.user.User;
import com.geoffrey.mini_trello_back.user.UserRepository;
import com.geoffrey.mini_trello_back.user.exceptions.UserDoesNotExistsException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;
    private final UserRepository userRepository;
    private final ProjectMapper projectMapper;
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public ProfileService(ProfileRepository profileRepository, UserRepository userRepository, ProfileMapper profileMapper, ProjectMapper projectMapper, TaskRepository taskRepository, TaskMapper taskMapper) {
        this.profileRepository = profileRepository;
        this.profileMapper = profileMapper;
        this.userRepository = userRepository;
        this.projectMapper = projectMapper;
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    public ProfileResponseDto createProfile(CreateProfileDto profileDto) {
        Integer userId = profileDto.userId();
        User userRequested = userRepository.findUserById(userId)
                .orElseThrow(() -> new UserDoesNotExistsException(userId));
        long nbProfiles = profileRepository.countByUser_Id(userId);
        if (nbProfiles > 0) {
            throw new ProfileUserAlreadyExistsException(userId);
        }
        Profile newProfile = profileMapper.userToProfile(userRequested, profileDto.dateOfBirth(), profileDto.role());
        Profile profile = profileRepository.save(newProfile);

        return profileMapper.toProfileResponseDto(profile);
    }

    public List<ProfileResponseDto> listProfiles() {
        List<Profile> profiles = profileRepository.findAll();
        return profileMapper.toListProfileResponseDto(profiles);
    }

    public ProfileResponseDto getProfile(int profileId) {
        Profile profile = profileRepository.findById(profileId).orElseThrow(() -> new ProfileNotFoundException(profileId));
        return profileMapper.toProfileResponseDto(profile);
    }

    public ProfileResponseDto patchProfile(PatchProfileDto profileDto, int profileId) {
        Profile profile = profileRepository.findById(profileId).orElseThrow(() -> new ProfileNotFoundException(profileId));
        profile.setDateOfBirth(profileDto.dateOfBirth());
        Profile newProfile = profileRepository.save(profile);
        return profileMapper.toProfileResponseDto(newProfile);
    }

    public List<SimpleProjectDto> getProjectsProfile(Integer profileId) {

        profileRepository.findById(profileId).orElseThrow(() -> new ProfileNotFoundException(profileId));
        List<Project> projects = profileRepository.findRelatedProjects(profileId);
        return projectMapper.toListSimpleProjectDto(projects);
    }

    public List<ProfileTasksDto> getTasksProfiles(int profileId) {
        profileRepository.findById(profileId).orElseThrow(() -> new ProfileNotFoundException(profileId));
        List<Task> tasks = taskRepository.findTasksByAssignedToId(profileId);
        return taskMapper.toListProfileTasksDto(tasks);
    }
}
