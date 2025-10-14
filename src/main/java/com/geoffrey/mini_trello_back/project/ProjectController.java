package com.geoffrey.mini_trello_back.project;

import com.geoffrey.mini_trello_back.common.ResponsePaginatedDto;
import com.geoffrey.mini_trello_back.profile.dto.SimpleProfileResponseDto;
import com.geoffrey.mini_trello_back.project.dto.CreateProjectDto;
import com.geoffrey.mini_trello_back.project.dto.PatchProjectDto;
import com.geoffrey.mini_trello_back.project.dto.PatchProjectOwnerDto;
import com.geoffrey.mini_trello_back.project.dto.ProjectResponseDto;
import com.geoffrey.mini_trello_back.task.dto.SimpleTaskResponseDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/projects/")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("")
    public ProjectResponseDto createProject(@Valid @RequestBody CreateProjectDto projectDto) {
        return projectService.createProject(projectDto);
    }

    @GetMapping("")
    public ResponsePaginatedDto<List<ProjectResponseDto>> getProjects(@PageableDefault Pageable pageable) {
        return projectService.listProjects(pageable);
    }

    @GetMapping("{projectId}")
    public ProjectResponseDto getProject(@PathVariable("projectId") Integer projectId) {
        return projectService.getProject(projectId);
    }

    @PatchMapping("{projectId}")
    public ProjectResponseDto patchProject(@PathVariable("projectId") Integer projectId, @Valid @RequestBody PatchProjectDto projectDto) {
        return projectService.patchProject(projectId, projectDto);
    }

    @PatchMapping("{projectId}/owner")
    public ProjectResponseDto patchOwnerProject(@PathVariable("projectId") Integer projectId, @Valid @RequestBody PatchProjectOwnerDto projectDto) {
        return projectService.patchProjectOwner(projectId, projectDto);
    }

    @DeleteMapping("{projectId}")
    public void patchProject(@PathVariable("projectId") Integer projectId) {
        projectService.deleteProject(projectId);
    }

    @GetMapping("{projectId}/tasks")
    public ResponsePaginatedDto<List<SimpleTaskResponseDto>> getProjectTasks(@PathVariable Integer projectId, @PageableDefault Pageable pageable) {
        return projectService.listProjectTasks(projectId, pageable);
    }

    @GetMapping("{projectId}/members")
    public ResponsePaginatedDto<List<SimpleProfileResponseDto>> getProjectMembers(@PathVariable Integer projectId, @PageableDefault Pageable pageable) {
        return projectService.listProjectMembers(projectId, pageable);
    }

}
