package com.geoffrey.mini_trello_back.task;

import com.geoffrey.mini_trello_back.profile.Profile;
import com.geoffrey.mini_trello_back.profile.ProfileMapper;
import com.geoffrey.mini_trello_back.profile.dto.SimpleProfileResponseDto;
import com.geoffrey.mini_trello_back.project.Project;
import com.geoffrey.mini_trello_back.project.ProjectMapper;
import com.geoffrey.mini_trello_back.project.dto.ProjectResponseDto;
import com.geoffrey.mini_trello_back.project.dto.ProjectTasksResponseDto;
import com.geoffrey.mini_trello_back.project.dto.SimpleProjectDto;
import com.geoffrey.mini_trello_back.task.dto.CreateTaskDto;
import com.geoffrey.mini_trello_back.task.dto.ProfileTasksDto;
import com.geoffrey.mini_trello_back.task.dto.SimpleTaskResponseDto;
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

    public ProjectTasksResponseDto toProjectTasksResponseDto(List<Task> tasks, Project project) {
        ProjectResponseDto projectResponseDto = projectMapper.toProjectResponseDto(project);
        List<SimpleTaskResponseDto> tasksDto = tasks.stream().map(this::toSimpleTaskResponseDto).toList();

        return new ProjectTasksResponseDto(projectResponseDto, tasksDto);
    }

    public SimpleTaskResponseDto toSimpleTaskResponseDto(Task task) {
        SimpleProfileResponseDto profile = profileMapper.toSimpleProfileResponseDto(task.getAssignedTo());
        return new SimpleTaskResponseDto(task.getId(), task.getTitle(), task.getDescription(), task.getStatus().toString(), profile);
    }

    public List<SimpleTaskResponseDto> toListSimpleTaskResponseDto(List<Task> tasks) {
        return tasks.stream().map(this::toSimpleTaskResponseDto).toList();
    }

    public ProfileTasksDto toProfileTasksDto(Task task) {
        SimpleProjectDto projectDto = projectMapper.toSimpleProjectDto(task.getProject());
        return new ProfileTasksDto(task.getId(), task.getTitle(), task.getDescription(), task.getStatus().toString(), projectDto);
    }

    public List<ProfileTasksDto> toListProfileTasksDto(List<Task> tasks) {
        return tasks.stream().map(this::toProfileTasksDto).toList();
    }

}
