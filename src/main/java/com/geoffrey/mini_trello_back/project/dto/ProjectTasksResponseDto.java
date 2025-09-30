package com.geoffrey.mini_trello_back.project.dto;

import com.geoffrey.mini_trello_back.task.dto.SimpleTaskResponseDto;

import java.util.List;

public record ProjectTasksResponseDto(ProjectResponseDto project, List<SimpleTaskResponseDto> tasks) {
}
