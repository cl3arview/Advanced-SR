package org.advancedsr.mappers;

import org.advancedsr.dtos.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO userToUserDTO(org.advancedsr.entities.User user);
    org.advancedsr.entities.User userDTOToUser(UserDTO userDTO);
}
