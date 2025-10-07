package com.geoffrey.mini_trello_back.task;

import com.geoffrey.mini_trello_back.profile.Profile;
import com.geoffrey.mini_trello_back.profile.ProfileMapper;
import com.geoffrey.mini_trello_back.profile.dto.SimpleProfileResponseDto;
import com.geoffrey.mini_trello_back.project.Project;
import com.geoffrey.mini_trello_back.project.ProjectMapper;
import com.geoffrey.mini_trello_back.project.dto.ProjectResponseDto;
import com.geoffrey.mini_trello_back.project.dto.SimpleProjectDto;
import com.geoffrey.mini_trello_back.task.dto.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class TaskMapper {
    private final ProfileMapper profileMapper;
    private final ProjectMapper projectMapper;

    public TaskMapper(ProfileMapper profileMapper, ProjectMapper projectMapper) {
        this.profileMapper = profileMapper;
        this.projectMapper = projectMapper;
    }

    public TaskResponseDto toTaskResponseDto(Task task) {
        Integer id = task.getId();
        String title = task.getTitle();
        String description = task.getDescription();
        String status = task.getStatus().toString();
        SimpleProfileResponseDto assignedTo = null;

        if (task.getAssignedTo() != null) {
            assignedTo = profileMapper.toSimpleProfileResponseDto(task.getAssignedTo());
        }

        ProjectResponseDto project = projectMapper.toProjectResponseDto(task.getProject());

        return new TaskResponseDto(
                id,
                title,
                description,
                status,
                assignedTo,
                project
        );
    }

    public Task toTask(CreateTaskDto taskDto, Profile profile, Project project) {
        Task task = new Task();
        task.setTitle(taskDto.title());
        task.setDescription(taskDto.description());
        if (taskDto.status() == null) {
            task.setStatus(Status.TODO);
        } else {
            task.setStatus(Status.valueOf(taskDto.status()));
        }
        task.setAssignedTo(profile);
        task.setProject(project);
        return task;
    }

    public SimpleTaskResponseDto toSimpleTaskResponseDto(Task task) {
        SimpleProfileResponseDto profile = profileMapper.toSimpleProfileResponseDto(task.getAssignedTo());
        return new SimpleTaskResponseDto(task.getId(), task.getTitle(), task.getDescription(), task.getStatus().toString(), profile);
    }

    public ProfileTasksDto toProfileTasksDto(Task task) {
        SimpleProjectDto projectDto = projectMapper.toSimpleProjectDto(task.getProject());
        return new ProfileTasksDto(task.getId(), task.getTitle(), task.getDescription(), task.getStatus().toString(), projectDto);
    }


    public void mergeTask(Task task, UpdateTaskDto taskDto) {
        if (StringUtils.hasLength(taskDto.title())) {
            task.setTitle(taskDto.title());
        }
        if (taskDto.description() != null) {
            task.setDescription(taskDto.description());
        }
    }
}
