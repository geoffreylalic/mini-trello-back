package com.geoffrey.mini_trello_back.role;

import com.geoffrey.mini_trello_back.role.dto.RoleDto;
import com.geoffrey.mini_trello_back.role.dto.RoleResponseDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {
    private final RoleServiceImpl roleService;

    public RoleController(RoleServiceImpl roleService) {
        this.roleService = roleService;
    }

    @PostMapping()
    RoleResponseDto createRole(@Valid RoleDto roleDto) {
        return roleService.createRole(roleDto);
    }

    @GetMapping()
    List<RoleResponseDto> listRoles() {
        return roleService.listRoles();

    }

    @GetMapping("/{roleId}")
    RoleResponseDto getRole(@PathVariable Integer roleId) {
        return roleService.getRole(roleId);
    }

    @PatchMapping("/{roleId}")
    RoleResponseDto updateRole(RoleDto roleDto, @PathVariable Integer roleId) {
        return roleService.updateRole(roleDto, roleId);
    }

    @DeleteMapping("/{roleId}")
    void deleteRole(@PathVariable Integer roleId) {
        roleService.deleteRole(roleId);
    }
}
