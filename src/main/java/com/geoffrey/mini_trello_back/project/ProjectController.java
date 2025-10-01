package com.geoffrey.mini_trello_back.project;

import com.geoffrey.mini_trello_back.profile.dto.SimpleProfileResponseDto;
import com.geoffrey.mini_trello_back.project.dto.*;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("projects")
    public ProjectResponseDto createProject(@Valid @RequestBody CreateProjectDto projectDto) {
        return projectService.createProject(projectDto);
    }

    @GetMapping("projects")
    public List<ProjectResponseDto> getProjects() {
        return projectService.listProjects();
    }

    @GetMapping("projects/{projectId}")
    public ProjectResponseDto getProject(@PathVariable("projectId") Integer projectId) {
        return projectService.getProject(projectId);
    }

    @PatchMapping("projects/{projectId}")
    public ProjectResponseDto patchProject(@PathVariable("projectId") Integer projectId, @Valid @RequestBody PatchProjectDto projectDto) {
        return projectService.patchProject(projectId, projectDto);
    }

    @PatchMapping("projects/{projectId}/owner")
    public ProjectResponseDto patchOwnerProject(@PathVariable("projectId") Integer projectId, @Valid @RequestBody PatchProjectOwnerDto projectDto) {
        return projectService.patchProjectOwner(projectId, projectDto);
    }

    @DeleteMapping("projects/{projectId}")
    public void patchProject(@PathVariable("projectId") Integer projectId) {
        projectService.deleteProject(projectId);
    }

    @GetMapping("projects/{projectId}/tasks")
    public ProjectTasksResponseDto getProjectTasks(@PathVariable Integer projectId) {
        return projectService.listProjectTasks(projectId);
    }

    @GetMapping("projects/{projectId}/members")
    public ProjectMembersDto getProjectMembers(@PathVariable Integer projectId) {
        return projectService.listProjectMembers(projectId);
    }


}
