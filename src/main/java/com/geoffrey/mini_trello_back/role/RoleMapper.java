package com.geoffrey.mini_trello_back.role;

import com.geoffrey.mini_trello_back.role.dto.RoleDto;
import com.geoffrey.mini_trello_back.role.dto.RoleResponseDto;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    Role toRole(RoleDto roleDto) {
        return new Role(roleDto.name());
    }

    public RoleResponseDto toRoleResponseDto(Role role) {
        return new RoleResponseDto(role.getId(), role.getName());
    }
}
