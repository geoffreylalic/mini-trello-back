package com.geoffrey.mini_trello_back.project;

import com.geoffrey.mini_trello_back.project.dto.CreateProjectDto;
import com.geoffrey.mini_trello_back.project.dto.ProjectResponseDto;
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

}
