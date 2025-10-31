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
import com.geoffrey.mini_trello_back.project.dto.*;
import com.geoffrey.mini_trello_back.project.exceptions.ProjectNotFoundException;
import com.geoffrey.mini_trello_back.task.Status;
import com.geoffrey.mini_trello_back.task.Task;
import com.geoffrey.mini_trello_back.task.TaskMapper;
import com.geoffrey.mini_trello_back.task.TaskRepository;
import com.geoffrey.mini_trello_back.task.dto.SimpleTaskResponseDto;
import com.geoffrey.mini_trello_back.user.User;
import com.geoffrey.mini_trello_back.user.UserMapper;
import com.geoffrey.mini_trello_back.user.dto.ProfileProjectMember;
import com.geoffrey.mini_trello_back.user.dto.ProjectTaskStatsDto;
import com.geoffrey.mini_trello_back.user.dto.UserResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    private final ProfileRepository profileRepository;
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;
    private final ProfileMapper profileMapper;
    private final ResponsePaginatedMapper responsePaginatedMapper;
    private final UserMapper userMapper;

    public ProjectService(ProjectRepository projectRepository,
                          ProfileRepository profileRepository,
                          ProjectMapper projectMapper,
                          TaskMapper taskMapper,
                          TaskRepository taskRepository,
                          ProfileMapper profileMapper, ResponsePaginatedMapper responsePaginatedMapper, UserMapper userMapper) {
        this.projectRepository = projectRepository;
        this.profileRepository = profileRepository;
        this.projectMapper = projectMapper;
        this.taskMapper = taskMapper;
        this.taskRepository = taskRepository;
        this.profileMapper = profileMapper;
        this.responsePaginatedMapper = responsePaginatedMapper;
        this.userMapper = userMapper;
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

        if (!Objects.equals(project.getOwner().getId(), profile.getId())) {
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

        if (!Objects.equals(project.getOwner().getId(), profile.getId())) {
            throw new AccessDeniedException();
        }

        projectRepository.delete(project);
    }

    public List<SimpleTaskResponseDto> listProjectTasks(Integer projectId, User currentUser, String filteredStatus) {
        Profile profile = AuthUtils.getProfileFromUser(currentUser);
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException(projectId));
        checkProjectRelatedToProfile(profile, project);

        Status status = null;
        if (filteredStatus != null && !filteredStatus.isBlank()) {
            try {
                status = Status.valueOf(filteredStatus);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException(e);
            }
            return taskRepository
                    .findTasksByProjectIdAndStatus(projectId, status).stream()
                    .map(taskMapper::toSimpleTaskResponseDto).toList();
        }

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

    public List<ProjectsSummaryResponseDto> getSummary(User currentUser) {
        Profile profile = AuthUtils.getProfileFromUser(currentUser);

        List<SimpleProjectDto> projects = projectRepository.findProjectIdsByOwnerId(profile.getId()).stream().toList();

        List<Integer> projectIds = projects.stream().map(SimpleProjectDto::id).toList();
        List<ProjectTaskStatsDto> projectTaskStatsDtoList = projectRepository.findProjectTasksStatsByIds(projectIds).stream().toList();
        List<ProfileProjectMember> members = profileRepository.findProfilesByRelatedProjectIds(projectIds);

        Map<Integer, List<UserResponseDto>> mappedProfileByProjectId = members.stream()
                .collect(
                        Collectors.groupingBy(ProfileProjectMember::projectId,
                                Collectors.mapping(prof -> userMapper.toUserResponse(prof.profile().getUser()), Collectors.toList())
                        )
                );


        return projects.stream().map(proj -> {
            ProjectTaskStatsDto projectTaskStatsDto = projectTaskStatsDtoList.stream().filter(p -> Objects.equals(p.id(), proj.id())).findFirst().orElse(new ProjectTaskStatsDto(proj.id(), 0, 0));
            float progress = 0;
            if (projectTaskStatsDto.totalTasks() > 0) {
                progress = (float) projectTaskStatsDto.totalTasksDone() / projectTaskStatsDto.totalTasks() * 100;
            }
            return new ProjectsSummaryResponseDto(proj.id(), proj.name(), userMapper.toUserResponse(currentUser), projectTaskStatsDto.totalTasks(), progress, mappedProfileByProjectId.getOrDefault(proj.id(), new ArrayList<>()));
        }).toList();
    }
}
