package com.geoffrey.mini_trello_back.task.dto;

import com.geoffrey.mini_trello_back.profile.dto.SimpleProfileResponseDto;
import com.geoffrey.mini_trello_back.project.dto.ProjectResponseDto;

public record TaskResponseDto(
        Integer id,
        String title,
        String description,
        String status,
        SimpleProfileResponseDto assignedTo,
        ProjectResponseDto project) {
}
