package com.geoffrey.mini_trello_back.role;

import com.geoffrey.mini_trello_back.role.dto.RoleDto;
import com.geoffrey.mini_trello_back.role.dto.RoleResponseDto;
import com.geoffrey.mini_trello_back.role.exceptions.RoleAlreadyExistsException;
import com.geoffrey.mini_trello_back.role.exceptions.RoleNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public RoleResponseDto createRole(RoleDto roleDto) {
        boolean exists = this.roleRepository.existsByName(roleDto.name());
        if (exists) {
            throw new RoleAlreadyExistsException(roleDto.name());
        }
        Role role = roleMapper.toRole(roleDto);
        roleRepository.save(role);
        return roleMapper.toRoleResponseDto(role);
    }

    @Override
    public List<RoleResponseDto> listRoles() {
        return roleRepository.findAll().stream().map(roleMapper::toRoleResponseDto).toList();
    }

    @Override
    public RoleResponseDto getRole(Integer roleId) {
        return roleRepository.findById(roleId).map(roleMapper::toRoleResponseDto).orElseThrow(() -> new RoleNotFoundException(roleId));
    }

    @Override
    public RoleResponseDto updateRole(RoleDto roleDto, Integer roleId) {
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new RoleNotFoundException(roleId));
        role.setName(roleDto.name());
        Role savedRole = roleRepository.save(role);
        return roleMapper.toRoleResponseDto(savedRole);
    }

    @Override
    public void deleteRole(Integer roleId) {
        boolean exists = roleRepository.existsById(roleId);
        if (exists) {
            throw new RoleNotFoundException(roleId);
        }
        roleRepository.deleteById(roleId);
    }
}
