package org.advancedsr.mappers;

import org.advancedsr.dtos.RoleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    RoleDTO roleToRoleDTO(org.advancedsr.entities.Role role);
    org.advancedsr.entities.Role roleDTOToRole(RoleDTO roleDTO);
}