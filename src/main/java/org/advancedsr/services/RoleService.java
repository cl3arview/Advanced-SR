package org.advancedsr.services;

import org.advancedsr.dtos.RoleDTO;
import org.advancedsr.entities.Role;
import org.advancedsr.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<RoleDTO> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public RoleDTO getRoleByName(String roleName) {
        Role role = roleRepository.findById(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        return convertToDTO(role);
    }

    public RoleDTO createRole(RoleDTO roleDTO) {
        Role role = convertToEntity(roleDTO);
        role = roleRepository.save(role);
        return convertToDTO(role);
    }

    public RoleDTO updateRole(String roleName, RoleDTO roleDTO) {
        Role role = roleRepository.findById(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        role.setPermissions(roleDTO.getPermissions());
        role = roleRepository.save(role);
        return convertToDTO(role);
    }

    public void deleteRole(String roleName) {
        roleRepository.deleteById(roleName);
    }

    private RoleDTO convertToDTO(Role role) {
        RoleDTO dto = new RoleDTO();
        dto.setRoleName(role.getRoleName());
        dto.setPermissions(role.getPermissions());
        return dto;
    }

    private Role convertToEntity(RoleDTO dto) {
        return Role.builder()
                .roleName(dto.getRoleName())
                .permissions(dto.getPermissions())
                .build();
    }
}