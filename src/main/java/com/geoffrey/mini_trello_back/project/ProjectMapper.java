package com.geoffrey.mini_trello_back.project;

import com.geoffrey.mini_trello_back.profile.Profile;
import com.geoffrey.mini_trello_back.profile.ProfileMapper;
import com.geoffrey.mini_trello_back.profile.ProfileRepository;
import com.geoffrey.mini_trello_back.profile.dto.SimpleProfileResponseDto;
import com.geoffrey.mini_trello_back.project.dto.CreateProjectDto;
import com.geoffrey.mini_trello_back.project.dto.ProjectResponseDto;
import com.geoffrey.mini_trello_back.project.dto.SimpleProjectDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectMapper {
    private final ProfileMapper profileMapper;

    public ProjectMapper(ProfileMapper profileMapper, ProfileRepository profileRepository) {
        this.profileMapper = profileMapper;
    }

    public ProjectResponseDto toProjectResponseDto(Project project) {
        Integer id = project.getId();
        String name = project.getName();
        String description = project.getDescription();
        SimpleProfileResponseDto owner = profileMapper.toSimpleProfileResponseDto(project.getOwner());
        return new ProjectResponseDto(id, name, description, owner);
    }

    public List<ProjectResponseDto> toListProjectResponseDto(List<Project> projects) {
        return projects.stream().map(this::toProjectResponseDto).toList();
    }

    public SimpleProjectDto toSimpleProjectDto(Project project) {
        return new SimpleProjectDto(project.getId(), project.getName(), project.getDescription());
    }

    public List<SimpleProjectDto> toListSimpleProjectDto(List<Project> projects) {
        return projects.stream().map(this::toSimpleProjectDto).toList();
    }

    public Project toProject(CreateProjectDto projectDto, Profile owner) {
        Integer profileId = projectDto.profileId();
        String name = projectDto.name();
        String description = projectDto.description();

        return new Project(name, description, owner);
    }
}
