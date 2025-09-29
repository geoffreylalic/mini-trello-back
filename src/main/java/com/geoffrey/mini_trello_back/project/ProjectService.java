package com.geoffrey.mini_trello_back.project;

import com.geoffrey.mini_trello_back.profile.Profile;
import com.geoffrey.mini_trello_back.profile.ProfileRepository;
import com.geoffrey.mini_trello_back.profile.exceptions.ProfileNotFoundException;
import com.geoffrey.mini_trello_back.project.dto.CreateProjectDto;
import com.geoffrey.mini_trello_back.project.dto.ProjectResponseDto;
import com.geoffrey.mini_trello_back.project.exceptions.ProjecNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    private final ProfileRepository profileRepository;
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    public ProjectService(ProjectRepository projectRepository, ProfileRepository profileRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.profileRepository = profileRepository;
        this.projectMapper = projectMapper;
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
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjecNotFoundException(projectId));
        return projectMapper.toProjectResponseDto(project);
    }
}
