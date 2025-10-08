package com.geoffrey.mini_trello_back.role;

import com.geoffrey.mini_trello_back.role.dto.RoleDto;
import com.geoffrey.mini_trello_back.role.dto.RoleResponseDto;

import java.util.List;

public interface RoleService {
    RoleResponseDto createRole(RoleDto roleDto);

    List<RoleResponseDto> listRoles();

    RoleResponseDto getRole(Integer roleId);

    RoleResponseDto updateRole(RoleDto roleDto, Integer roleId);

    void deleteRole(Integer roleId);

}
