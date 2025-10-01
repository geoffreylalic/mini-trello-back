package com.geoffrey.mini_trello_back.project.dto;

import com.geoffrey.mini_trello_back.profile.dto.SimpleProfileResponseDto;

import java.util.List;

public record ProjectMembersDto(SimpleProfileResponseDto owner, List<SimpleProfileResponseDto> members) {
}
