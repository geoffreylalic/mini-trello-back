package com.geoffrey.mini_trello_back.task;

import com.geoffrey.mini_trello_back.common.ResponsePaginatedDto;
import com.geoffrey.mini_trello_back.task.dto.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    public ResponsePaginatedDto<List<TaskResponseDto>> getTasks(@PageableDefault Pageable pageable) {
        return taskService.getTasks(pageable);
    }

    @PostMapping("")
    public TaskResponseDto createTask(@Valid @RequestBody CreateTaskDto taskDto) {
        return taskService.createTask(taskDto);
    }

    @GetMapping("{task_id}")
    public TaskResponseDto getTask(@PathVariable("task_id") Integer taskId) {
        return taskService.getTask(taskId);
    }

    @PatchMapping("{task_id}")
    public TaskResponseDto patchTask(@PathVariable("task_id") Integer taskId, @Valid @RequestBody UpdateTaskDto taskDto) {
        return taskService.patchTask(taskId, taskDto);
    }

    @PatchMapping("{task_id}/status")
    public TaskResponseDto patchStatusTask(@PathVariable("task_id") Integer taskId, @Valid @RequestBody UpdateStatusTaskDto taskDto) {
        return taskService.patchStatusTask(taskId, taskDto);
    }

    @PatchMapping("{task_id}/assigned-to")
    public TaskResponseDto patchAssignedTask(@PathVariable("task_id") Integer taskId, @Valid @RequestBody UpdateAssignedTaskDto taskDto) {
        return taskService.patchAssignedTask(taskId, taskDto);
    }


    @DeleteMapping("{task_id}")
    public void deleteTask(@PathVariable("task_id") Integer taskId) {
        taskService.deleteTask(taskId);
    }
}
