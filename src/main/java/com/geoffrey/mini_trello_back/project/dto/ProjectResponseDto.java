package com.geoffrey.mini_trello_back.project.dto;

import com.geoffrey.mini_trello_back.profile.dto.SimpleProfileResponseDto;

public record ProjectResponseDto(Integer id,
                                 String name,
                                 String description,
                                 SimpleProfileResponseDto owner) {
}
