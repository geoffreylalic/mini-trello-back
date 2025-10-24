package com.geoffrey.mini_trello_back.project;

import com.geoffrey.mini_trello_back.auth.AuthUtils;
import com.geoffrey.mini_trello_back.auth.exceptions.AccessDeniedException;
import com.geoffrey.mini_trello_back.common.ResponsePaginatedDto;
import com.geoffrey.mini_trello_back.common.ResponsePaginatedMapper;
import com.geoffrey.mini_trello_back.profile.Profile;
import com.geoffrey.mini_trello_back.profile.ProfileMapper;
import com.geoffrey.mini_trello_back.profile.ProfileRepository;
import com.geoffrey.mini_trello_back.profile.dto.SimpleProfileResponseDto;
import com.geoffrey.mini_trello_back.profile.exceptions.ProfileNotFoundException;
import com.geoffrey.mini_trello_back.project.dto.CreateProjectDto;
import com.geoffrey.mini_trello_back.project.dto.PatchProjectDto;
import com.geoffrey.mini_trello_back.project.dto.PatchProjectOwnerDto;
import com.geoffrey.mini_trello_back.project.dto.ProjectResponseDto;
import com.geoffrey.mini_trello_back.project.exceptions.ProjectNotFoundException;
import com.geoffrey.mini_trello_back.task.Task;
import com.geoffrey.mini_trello_back.task.TaskMapper;
import com.geoffrey.mini_trello_back.task.TaskRepository;
import com.geoffrey.mini_trello_back.task.dto.SimpleTaskResponseDto;
import com.geoffrey.mini_trello_back.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ProjectService {
    private final ProfileRepository profileRepository;
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;
    private final ProfileMapper profileMapper;
    private final ResponsePaginatedMapper responsePaginatedMapper;

    public ProjectService(ProjectRepository projectRepository,
                          ProfileRepository profileRepository,
                          ProjectMapper projectMapper,
                          TaskMapper taskMapper,
                          TaskRepository taskRepository,
                          ProfileMapper profileMapper, ResponsePaginatedMapper responsePaginatedMapper) {
        this.projectRepository = projectRepository;
        this.profileRepository = profileRepository;
        this.projectMapper = projectMapper;
        this.taskMapper = taskMapper;
        this.taskRepository = taskRepository;
        this.profileMapper = profileMapper;
        this.responsePaginatedMapper = responsePaginatedMapper;
    }

    public ResponsePaginatedDto<List<ProjectResponseDto>> listProjects(Pageable pageable, User currentUser) {
        Profile profile = AuthUtils.getProfileFromUser(currentUser);
        List<Integer> projectIds = projectRepository.findProjectIdsByProfileId(profile.getId());
        Page<ProjectResponseDto> page = projectRepository.findProjectsRelatedByProfileId(projectIds, profile.getId(), pageable)
                .map(projectMapper::toProjectResponseDto);
        return responsePaginatedMapper.toResponsePaginatedDto(page, pageable);
    }

    public ProjectResponseDto createProject(CreateProjectDto projectDto, User currentUser) {
        Profile profile = AuthUtils.getProfileFromUser(currentUser);
        Project project = projectMapper.toProject(projectDto, profile);
        Project newProject = projectRepository.save(project);
        return projectMapper.toProjectResponseDto(newProject);
    }

    public ProjectResponseDto getProject(Integer projectId, User currentUser) {
        Profile profile = AuthUtils.getProfileFromUser(currentUser);
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException(projectId));

        checkProjectRelatedToProfile(profile, project);

        return projectMapper.toProjectResponseDto(project);
    }


    public ProjectResponseDto patchProject(Integer projectId, PatchProjectDto projectDto, User currentUser) {
        Profile profile = AuthUtils.getProfileFromUser(currentUser);
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException(projectId));

        if (!Objects.equals(project.getOwner(), profile)) {
            throw new AccessDeniedException();
        }

        project.setName(projectDto.name());
        if (projectDto.description() != null) {
            project.setDescription(projectDto.description());
        }

        Project newProject = projectRepository.save(project);
        return projectMapper.toProjectResponseDto(newProject);
    }

    public ProjectResponseDto patchProjectOwner(Integer projectId, PatchProjectOwnerDto projectDto, User currentUser) {
        Profile profile = AuthUtils.getProfileFromUser(currentUser);
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException(projectId));
        if (!Objects.equals(project.getOwner(), profile)) {
            throw new AccessDeniedException();
        }

        Integer profileId = projectDto.profileId();
        Profile newOwner = profileRepository.findById(profileId).orElseThrow(() -> new ProfileNotFoundException(profileId));
        project.setOwner(newOwner);

        Project newProject = projectRepository.save(project);
        return projectMapper.toProjectResponseDto(newProject);
    }

    public void deleteProject(Integer projectId, User currentUser) {
        Profile profile = AuthUtils.getProfileFromUser(currentUser);

        Project project = projectRepository
                .findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId));

        if (!Objects.equals(project.getOwner(), profile)) {
            throw new AccessDeniedException();
        }

        projectRepository.delete(project);
    }

    public List<SimpleTaskResponseDto> listProjectTasks(Integer projectId, User currentUser) {
        Profile profile = AuthUtils.getProfileFromUser(currentUser);
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException(projectId));
        checkProjectRelatedToProfile(profile, project);

        return taskRepository
                .findTasksByProjectId(projectId).stream()
                .map(taskMapper::toSimpleTaskResponseDto).toList();
    }


    public ResponsePaginatedDto<List<SimpleProfileResponseDto>> listProjectMembers(Integer projectId, Pageable pageable, User currentUser) {
        Profile profile = AuthUtils.getProfileFromUser(currentUser);
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException(projectId));
        checkProjectRelatedToProfile(profile, project);

        Page<SimpleProfileResponseDto> page = profileRepository
                .findProfilesByRelatedProject(projectId, pageable)
                .map(profileMapper::toSimpleProfileResponseDto);
        return responsePaginatedMapper.toResponsePaginatedDto(page, pageable);
    }

    private void checkProjectRelatedToProfile(Profile profile, Project project) {
        if (Objects.equals(project.getOwner().getId(), profile.getId())) {
            return;
        }

        if (profile.getTasks().isEmpty()) {
            throw new AccessDeniedException();
        }

        List<Integer> taskIds = profile.getTasks().stream().map(Task::getId).toList();
        List<Integer> projectIds = projectRepository.findProjectIdsByTaskIds(taskIds);
        if (!projectIds.contains(project.getId())) {
            throw new AccessDeniedException();
        }
    }

}
