package com.geoffrey.mini_trello_back.project;

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
import com.geoffrey.mini_trello_back.task.TaskMapper;
import com.geoffrey.mini_trello_back.task.TaskRepository;
import com.geoffrey.mini_trello_back.task.dto.SimpleTaskResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public ResponsePaginatedDto<List<ProjectResponseDto>> listProjects(Pageable pageable) {
        Page<ProjectResponseDto> page = projectRepository.findAll(pageable).map(projectMapper::toProjectResponseDto);
        return responsePaginatedMapper.toResponsePaginatedDto(page, pageable);
    }

    public ProjectResponseDto createProject(CreateProjectDto projectDto) {
        Integer profileId = projectDto.profileId();
        Profile owner = profileRepository.findById(profileId).orElseThrow(() -> new ProfileNotFoundException((profileId)));
        Project project = projectMapper.toProject(projectDto, owner);
        Project newProject = projectRepository.save(project);
        return projectMapper.toProjectResponseDto(newProject);
    }

    public ProjectResponseDto getProject(Integer projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException(projectId));
        return projectMapper.toProjectResponseDto(project);
    }

    public ProjectResponseDto patchProject(Integer projectId, PatchProjectDto projectDto) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException(projectId));

        project.setName(projectDto.name());
        if (projectDto.description() != null) {
            project.setDescription(projectDto.description());
        }

        Project newProject = projectRepository.save(project);
        return projectMapper.toProjectResponseDto(newProject);
    }

    public ProjectResponseDto patchProjectOwner(Integer projectId, PatchProjectOwnerDto projectDto) {
        Integer profileId = projectDto.profileId();

        Profile profile = profileRepository.findById(profileId).orElseThrow(() -> new ProfileNotFoundException(profileId));
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException(projectId));

        project.setOwner(profile);

        Project newProject = projectRepository.save(project);
        return projectMapper.toProjectResponseDto(newProject);
    }

    public void deleteProject(Integer projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException(projectId));
        projectRepository.delete(project);
    }

    public ResponsePaginatedDto<List<SimpleTaskResponseDto>> listProjectTasks(Integer projectId, Pageable pageable) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException(projectId));
        Page<SimpleTaskResponseDto> page = taskRepository.findTasksByProjectId(projectId, pageable).map(taskMapper::toSimpleTaskResponseDto);
        return responsePaginatedMapper.toResponsePaginatedDto(page, pageable);
    }

    public ResponsePaginatedDto<List<SimpleProfileResponseDto>> listProjectMembers(Integer projectId, Pageable pageable) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException(projectId));
        Page<SimpleProfileResponseDto> page = profileRepository.findProfilesByRelatedProject(projectId, pageable).map(profileMapper::toSimpleProfileResponseDto);
        return responsePaginatedMapper.toResponsePaginatedDto(page, pageable);
    }
}
