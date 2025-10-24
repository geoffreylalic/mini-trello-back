package com.geoffrey.mini_trello_back.task;

import com.geoffrey.mini_trello_back.common.ResponsePaginatedDto;
import com.geoffrey.mini_trello_back.task.dto.*;
import com.geoffrey.mini_trello_back.user.User;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/tasks/")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("")
    public ResponsePaginatedDto<List<TaskResponseDto>> getTasks(@PageableDefault Pageable pageable, @AuthenticationPrincipal User currentUser) {
        return taskService.getTasks(pageable, currentUser);
    }

    @PostMapping("")
    public TaskResponseDto createTask(@Valid @RequestBody CreateTaskDto taskDto, @AuthenticationPrincipal User currentUser) {
        return taskService.createTask(taskDto, currentUser);
    }

    @GetMapping("{task_id}")
    public TaskResponseDto getTask(@PathVariable("task_id") Integer taskId, @AuthenticationPrincipal User currentUser) {
        return taskService.getTask(taskId, currentUser);
    }

    @PatchMapping("{task_id}")
    public SimpleTaskResponseDto patchTask(@PathVariable("task_id") Integer taskId, @Valid @RequestBody UpdateTaskDto taskDto, @AuthenticationPrincipal User currentUser) {
        return taskService.patchTask(taskId, taskDto, currentUser);
    }

    @PatchMapping("{task_id}/status")
    public SimpleTaskResponseDto patchStatusTask(@PathVariable("task_id") Integer taskId, @Valid @RequestBody UpdateStatusTaskDto taskDto, @AuthenticationPrincipal User currentUser) {
        return taskService.patchStatusTask(taskId, taskDto, currentUser);
    }

    @PatchMapping("{task_id}/assigned-to")
    public TaskResponseDto patchAssignedTask(@PathVariable("task_id") Integer taskId, @Valid @RequestBody UpdateAssignedTaskDto taskDto, @AuthenticationPrincipal User currentUser) {
        return taskService.patchAssignedTask(taskId, taskDto, currentUser);
    }


    @DeleteMapping("{task_id}")
    public void deleteTask(@PathVariable("task_id") Integer taskId, @AuthenticationPrincipal User currentUser) {
        taskService.deleteTask(taskId, currentUser);
    }
}
