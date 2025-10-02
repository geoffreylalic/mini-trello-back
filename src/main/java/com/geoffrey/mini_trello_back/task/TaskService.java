package com.geoffrey.mini_trello_back.task;

import com.geoffrey.mini_trello_back.common.ResponsePaginatedDto;
import com.geoffrey.mini_trello_back.common.ResponsePaginatedMapper;
import com.geoffrey.mini_trello_back.profile.Profile;
import com.geoffrey.mini_trello_back.profile.ProfileRepository;
import com.geoffrey.mini_trello_back.profile.exceptions.ProfileNotFoundException;
import com.geoffrey.mini_trello_back.project.Project;
import com.geoffrey.mini_trello_back.project.ProjectRepository;
import com.geoffrey.mini_trello_back.project.exceptions.ProjectNotFoundException;
import com.geoffrey.mini_trello_back.task.dto.*;
import com.geoffrey.mini_trello_back.task.exceptions.TaskNotFoundException;
import com.geoffrey.mini_trello_back.task.exceptions.TaskStatusMismatchException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class TaskService {

    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;
    private final ProfileRepository profileRepository;
    private final ProjectRepository projectRepository;
    private final ResponsePaginatedMapper responsePaginatedMapper;

    public TaskService(TaskMapper taskMapper,
                       TaskRepository taskRepository,
                       ProfileRepository profileRepository,
                       ProjectRepository projectRepository, ResponsePaginatedMapper responsePaginatedMapper) {
        this.taskMapper = taskMapper;
        this.taskRepository = taskRepository;
        this.profileRepository = profileRepository;
        this.projectRepository = projectRepository;
        this.responsePaginatedMapper = responsePaginatedMapper;
    }

    public ResponsePaginatedDto<List<TaskResponseDto>> getTasks(Pageable pageable) {
        Page<TaskResponseDto> page = taskRepository.findAll(pageable).map(taskMapper::toTaskResponseDto);
        return responsePaginatedMapper.toResponsePaginatedDto(page, pageable);
    }

    public TaskResponseDto createTask(CreateTaskDto taskDto) {
        Integer profileId = taskDto.profileId();
        Integer projectId = taskDto.projectId();
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException(projectId));

        Profile profile = null;
        if (profileId != null) {
            profile = profileRepository.findById(profileId).orElseThrow(() -> new ProfileNotFoundException(profileId));
        }

        Task task = taskMapper.toTask(taskDto, profile, project);
        Task newTask = taskRepository.save(task);
        return taskMapper.toTaskResponseDto(newTask);
    }

    public TaskResponseDto getTask(Integer taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
        return taskMapper.toTaskResponseDto(task);
    }

    public TaskResponseDto patchTask(Integer taskId, UpdateTaskDto taskDto) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
        String title = taskDto.title();
        String description = taskDto.description();

        if (title != null) {
            task.setTitle(title);
        }
        if (description != null) {
            task.setDescription(description);
        }

        Task updateTask = taskRepository.save(task);

        return taskMapper.toTaskResponseDto(updateTask);
    }

    public TaskResponseDto patchStatusTask(Integer taskId, UpdateStatusTaskDto taskDto) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
        boolean anyMatch = Arrays.stream(Status.values()).anyMatch(status -> status.name().equals(taskDto.status()));
        if (!anyMatch) {
            throw new TaskStatusMismatchException(taskDto.status());
        }
        task.setStatus(Status.valueOf(taskDto.status()));
        Task updatedTask = taskRepository.save(task);
        return taskMapper.toTaskResponseDto(updatedTask);
    }

    public TaskResponseDto patchAssignedTask(Integer taskId, UpdateAssignedTaskDto taskDto) {
        Integer profileId = taskDto.profileId();
        Profile profile = profileRepository.findById(profileId).orElseThrow(() -> new ProfileNotFoundException(profileId));
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));

        task.setAssignedTo(profile);
        return taskMapper.toTaskResponseDto(task);
    }


    public void deleteTask(Integer taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
        taskRepository.delete(task);
    }


}
