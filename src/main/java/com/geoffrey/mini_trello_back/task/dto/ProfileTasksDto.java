package com.geoffrey.mini_trello_back.task.dto;

import com.geoffrey.mini_trello_back.project.dto.SimpleProjectDto;

public record ProfileTasksDto(Integer id,
                              String title,
                              String description,
                              String status,
                              SimpleProjectDto profile) {
}
