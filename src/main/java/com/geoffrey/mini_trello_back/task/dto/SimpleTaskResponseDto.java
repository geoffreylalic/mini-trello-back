package com.geoffrey.mini_trello_back.task.dto;

import com.geoffrey.mini_trello_back.profile.dto.SimpleProfileResponseDto;

public record SimpleTaskResponseDto(Integer id,
                                    String title,
                                    String description,
                                    String status,
                                    SimpleProfileResponseDto profile
) {
}
