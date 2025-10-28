package com.geoffrey.mini_trello_back.project;

import com.geoffrey.mini_trello_back.common.ResponsePaginatedDto;
import com.geoffrey.mini_trello_back.profile.dto.SimpleProfileResponseDto;
import com.geoffrey.mini_trello_back.project.dto.CreateProjectDto;
import com.geoffrey.mini_trello_back.project.dto.PatchProjectDto;
import com.geoffrey.mini_trello_back.project.dto.PatchProjectOwnerDto;
import com.geoffrey.mini_trello_back.project.dto.ProjectResponseDto;
import com.geoffrey.mini_trello_back.task.dto.SimpleTaskResponseDto;
import com.geoffrey.mini_trello_back.user.User;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ProjectResponseDto createProject(@Valid @RequestBody CreateProjectDto projectDto,
                                            @AuthenticationPrincipal User currentUser) {
        return projectService.createProject(projectDto, currentUser);
    }

    @GetMapping("")
    public ResponsePaginatedDto<List<ProjectResponseDto>> getProjects(@PageableDefault Pageable pageable,
                                                                      @AuthenticationPrincipal User currentUser) {
        return projectService.listProjects(pageable, currentUser);
    }

    @GetMapping("{projectId}")
    public ProjectResponseDto getProject(@PathVariable("projectId") Integer projectId,
                                         @AuthenticationPrincipal User currentUser) {
        return projectService.getProject(projectId, currentUser);
    }

    @PatchMapping("{projectId}")
    public ProjectResponseDto patchProject(@PathVariable("projectId") Integer projectId,
                                           @Valid @RequestBody PatchProjectDto projectDto,
                                           @AuthenticationPrincipal User currentUser) {
        return projectService.patchProject(projectId, projectDto, currentUser);
    }

    @PatchMapping("{projectId}/owner")
    public ProjectResponseDto patchOwnerProject(@PathVariable("projectId") Integer projectId,
                                                @Valid @RequestBody PatchProjectOwnerDto projectDto,
                                                @AuthenticationPrincipal User currentUser) {
        return projectService.patchProjectOwner(projectId, projectDto, currentUser);
    }

    @DeleteMapping("{projectId}")
    public void patchProject(@PathVariable("projectId") Integer projectId,
                             @AuthenticationPrincipal User currentUser) {
        projectService.deleteProject(projectId, currentUser);
    }

    @GetMapping("{projectId}/tasks")
    public List<SimpleTaskResponseDto> getProjectTasks(@PathVariable Integer projectId,
                                                       @AuthenticationPrincipal User currentUser,
                                                       @RequestParam(value = "status", required = false) String filteredStatus) {
        return projectService.listProjectTasks(projectId, currentUser, filteredStatus);
    }

    @GetMapping("{projectId}/members")
    public ResponsePaginatedDto<List<SimpleProfileResponseDto>> getProjectMembers(@PathVariable Integer projectId,
                                                                                  @PageableDefault Pageable pageable,
                                                                                  @AuthenticationPrincipal User currentUser) {
        return projectService.listProjectMembers(projectId, pageable, currentUser);
    }

}
