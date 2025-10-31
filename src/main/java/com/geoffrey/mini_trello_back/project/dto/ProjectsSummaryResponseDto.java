package com.geoffrey.mini_trello_back.project.dto;

import com.geoffrey.mini_trello_back.user.dto.UserResponseDto;

import java.util.List;

public record ProjectsSummaryResponseDto(Integer id,
                                         String name,
                                         UserResponseDto owner,
                                         long nbTask,
                                         float progression,
                                         List<UserResponseDto> members
) {
}