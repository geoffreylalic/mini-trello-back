package com.geoffrey.mini_trello_back.profile.dto;

import com.geoffrey.mini_trello_back.project.dto.SimpleProjectDto;
import com.geoffrey.mini_trello_back.task.Task;
import com.geoffrey.mini_trello_back.user.dto.UserResponseDto;

import java.time.LocalDate;
import java.util.List;

public record ProfileResponseDto(Integer id,
                                 UserResponseDto user,
                                 LocalDate dateOfBirth,
                                 String role,
                                 List<SimpleProjectDto> projects,
                                 List<Task> tasks) {
}
