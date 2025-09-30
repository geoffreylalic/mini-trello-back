package com.geoffrey.mini_trello_back.task;

import com.geoffrey.mini_trello_back.profile.Profile;
import com.geoffrey.mini_trello_back.profile.ProfileMapper;
import com.geoffrey.mini_trello_back.profile.dto.SimpleProfileResponseDto;
import com.geoffrey.mini_trello_back.project.Project;
import com.geoffrey.mini_trello_back.project.ProjectMapper;
import com.geoffrey.mini_trello_back.project.dto.ProjectResponseDto;
import com.geoffrey.mini_trello_back.task.dto.CreateTaskDto;
import com.geoffrey.mini_trello_back.task.dto.TaskResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public List<TaskResponseDto> toListTaskResponseDto(List<Task> tasks) {
        return tasks.stream().map(this::toTaskResponseDto).toList();
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
}
