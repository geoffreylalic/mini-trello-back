package com.geoffrey.mini_trello_back.user.dto;

public record ProjectTaskStatsDto(
        Integer id,
        long totalTasks,
        long totalTasksDone
) {
}
