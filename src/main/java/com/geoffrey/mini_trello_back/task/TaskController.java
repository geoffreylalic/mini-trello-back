package com.geoffrey.mini_trello_back.task;

import com.geoffrey.mini_trello_back.task.dto.*;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("tasks")
    public List<TaskResponseDto> getTasks() {
        return taskService.getTasks();
    }

    @PostMapping("tasks")
    public TaskResponseDto createTask(@Valid @RequestBody CreateTaskDto taskDto) {
        return taskService.createTask(taskDto);
    }

    @GetMapping("tasks/{task_id}")
    public TaskResponseDto getTask(@PathVariable("task_id") Integer taskId) {
        return taskService.getTask(taskId);
    }

    @PatchMapping("tasks/{task_id}")
    public TaskResponseDto patchTask(@PathVariable("task_id") Integer taskId, @Valid @RequestBody UpdateTaskDto taskDto) {
        return taskService.patchTask(taskId, taskDto);
    }

    @PatchMapping("tasks/{task_id}/status")
    public TaskResponseDto patchStatusTask(@PathVariable("task_id") Integer taskId, @Valid @RequestBody UpdateStatusTaskDto taskDto) {
        return taskService.patchStatusTask(taskId, taskDto);
    }

    @PatchMapping("tasks/{task_id}/assigned-to")
    public TaskResponseDto patchAssignedTask(@PathVariable("task_id") Integer taskId, @Valid @RequestBody UpdateAssignedTaskDto taskDto) {
        return taskService.patchAssignedTask(taskId, taskDto);
    }


    @DeleteMapping("tasks/{task_id}")
    public void deleteTask(@PathVariable("task_id") Integer taskId) {
        taskService.deleteTask(taskId);
    }
}
