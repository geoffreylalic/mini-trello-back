package com.geoffrey.mini_trello_back.project;

import com.geoffrey.mini_trello_back.profile.Profile;
import com.geoffrey.mini_trello_back.profile.ProfileMapper;
import com.geoffrey.mini_trello_back.profile.ProfileRepository;
import com.geoffrey.mini_trello_back.profile.dto.SimpleProfileResponseDto;
import com.geoffrey.mini_trello_back.profile.exceptions.ProfileNotFoundException;
import com.geoffrey.mini_trello_back.project.dto.*;
import com.geoffrey.mini_trello_back.project.exceptions.ProjectNotFoundException;
import com.geoffrey.mini_trello_back.task.Task;
import com.geoffrey.mini_trello_back.task.TaskMapper;
import com.geoffrey.mini_trello_back.task.TaskRepository;
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

    public ProjectService(ProjectRepository projectRepository,
                          ProfileRepository profileRepository,
                          ProjectMapper projectMapper,
                          TaskMapper taskMapper,
                          TaskRepository taskRepository,
                          ProfileMapper profileMapper) {
        this.projectRepository = projectRepository;
        this.profileRepository = profileRepository;
        this.projectMapper = projectMapper;
        this.taskMapper = taskMapper;
        this.taskRepository = taskRepository;
        this.profileMapper = profileMapper;
    }

    public List<ProjectResponseDto> listProjects() {
        List<Project> projects = projectRepository.findAll();
        return projectMapper.toListProjectResponseDto(projects);
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

    public ProjectTasksResponseDto listProjectTasks(Integer projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException(projectId));
        List<Task> tasks = taskRepository.findTasksByProjectId(projectId);
        return taskMapper.toProjectTasksResponseDto(tasks, project);
    }

    public ProjectMembersDto listProjectMembers(Integer projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException(projectId));
        List<Profile> profiles = profileRepository.findProfilesByRelatedProject(projectId);
        return profileMapper.toProjectMembersDto(project.getOwner(), profiles);
    }
}
