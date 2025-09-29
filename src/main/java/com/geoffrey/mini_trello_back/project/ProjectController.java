package com.geoffrey.mini_trello_back.project;

import com.geoffrey.mini_trello_back.project.dto.CreateProjectDto;
import com.geoffrey.mini_trello_back.project.dto.ProjectResponseDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

}
