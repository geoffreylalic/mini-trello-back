package com.geoffrey.mini_trello_back.task;

import com.geoffrey.mini_trello_back.auth.AuthUtils;
import com.geoffrey.mini_trello_back.common.ResponsePaginatedDto;
import com.geoffrey.mini_trello_back.common.ResponsePaginatedMapper;
import com.geoffrey.mini_trello_back.profile.Profile;
import com.geoffrey.mini_trello_back.profile.ProfileRepository;
import com.geoffrey.mini_trello_back.profile.exceptions.ProfileNotFoundException;
import com.geoffrey.mini_trello_back.project.Project;
import com.geoffrey.mini_trello_back.project.ProjectRepository;
import com.geoffrey.mini_trello_back.project.exceptions.ProjectNotFoundException;
import com.geoffrey.mini_trello_back.task.dto.*;
import com.geoffrey.mini_trello_back.task.exceptions.ProfileNotRelatedToProjectException;
import com.geoffrey.mini_trello_back.task.exceptions.TaskNotFoundException;
import com.geoffrey.mini_trello_back.task.exceptions.TaskStatusMismatchException;
import com.geoffrey.mini_trello_back.user.User;
import com.geoffrey.mini_trello_back.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class TaskService {

    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;
    private final ProfileRepository profileRepository;
    private final ProjectRepository projectRepository;
    private final ResponsePaginatedMapper responsePaginatedMapper;
    private final UserRepository userRepository;

    public TaskService(TaskMapper taskMapper,
                       TaskRepository taskRepository,
                       ProfileRepository profileRepository,
                       ProjectRepository projectRepository, ResponsePaginatedMapper responsePaginatedMapper, UserRepository userRepository) {
        this.taskMapper = taskMapper;
        this.taskRepository = taskRepository;
        this.profileRepository = profileRepository;
        this.projectRepository = projectRepository;
        this.responsePaginatedMapper = responsePaginatedMapper;
        this.userRepository = userRepository;
    }

    public ResponsePaginatedDto<List<TaskResponseDto>> getTasks(Pageable pageable, User currentUser) {
        Profile profile = AuthUtils.getProfileFromUser(currentUser);

        Page<TaskResponseDto> page = taskRepository.findTasksByProfileId(profile.getId(), pageable).map(taskMapper::toTaskResponseDto);
        return responsePaginatedMapper.toResponsePaginatedDto(page, pageable);
    }

    public TaskResponseDto createTask(CreateTaskDto taskDto, User currentUser) {
        Integer projectId = taskDto.projectId();
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException(projectId));
        Profile profile = AuthUtils.getProfileFromUser(currentUser);

        if (!Objects.equals(project.getOwner().getId(), profile.getId())) {
            throw new ProfileNotRelatedToProjectException();
        }

        Task task = taskMapper.toTask(taskDto, profile, project);
        Task newTask = taskRepository.save(task);
        return taskMapper.toTaskResponseDto(newTask);
    }

    public TaskResponseDto getTask(Integer taskId, User currentUser) {
        Profile profile = AuthUtils.getProfileFromUser(currentUser);
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));

        checkProfileRelatedToProject(task, profile);
        return taskMapper.toTaskResponseDto(task);
    }

    private void checkProfileRelatedToProject(Task task, Profile profile) {
        if (!Objects.equals(task.getAssignedTo().getId(), profile.getId()) || !Objects.equals(task.getProject().getOwner().getId(), profile.getId())) {
            throw new ProfileNotRelatedToProjectException();
        }
    }

    public SimpleTaskResponseDto patchTask(Integer taskId, UpdateTaskDto taskDto, User currentUser) {
        Profile profile = AuthUtils.getProfileFromUser(currentUser);
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
        checkProfileRelatedToProject(task, profile);

        taskMapper.mergeTask(task, taskDto);
        Task updateTask = taskRepository.save(task);

        return taskMapper.toSimpleTaskResponseDto(updateTask);
    }

    public SimpleTaskResponseDto patchStatusTask(Integer taskId, UpdateStatusTaskDto taskDto, User currentUser) {
        Profile profile = AuthUtils.getProfileFromUser(currentUser);
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));

        checkProfileRelatedToProject(task, profile);

        boolean anyMatch = Arrays.stream(Status.values()).anyMatch(status -> status.name().equals(taskDto.status()));
        if (!anyMatch) {
            throw new TaskStatusMismatchException(taskDto.status());
        }
        task.setStatus(Status.valueOf(taskDto.status()));
        Task updatedTask = taskRepository.save(task);
        return taskMapper.toSimpleTaskResponseDto(updatedTask);
    }

    public TaskResponseDto patchAssignedTask(Integer taskId, UpdateAssignedTaskDto taskDto, User currentUser) {
        Profile profile = AuthUtils.getProfileFromUser(currentUser);
        String email = taskDto.email();
        User user = (User) userRepository.findUserByEmail(email).orElseThrow();
        Profile assignedProfile = user.getProfile();
        if (assignedProfile == null) {
            throw new ProfileNotFoundException();
        }
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));

        checkIsOwnerProject(profile, task);

        task.setAssignedTo(assignedProfile);
        taskRepository.save(task);
        return taskMapper.toTaskResponseDto(task);
    }

    private void checkIsOwnerProject(Profile profile, Task task) {
        if (!Objects.equals(task.getProject().getOwner().getId(), profile.getId())) {
            throw new ProfileNotRelatedToProjectException();
        }
    }

    public void deleteTask(Integer taskId, User currentUser) {
        Profile profile = AuthUtils.getProfileFromUser(currentUser);
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
        checkIsOwnerProject(profile, task);
        taskRepository.delete(task);
    }


}
