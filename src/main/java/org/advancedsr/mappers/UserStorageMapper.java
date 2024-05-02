package org.advancedsr.mappers;

import org.advancedsr.dtos.UserStorageDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserStorageMapper {
    UserStorageMapper INSTANCE = Mappers.getMapper(UserStorageMapper.class);

    UserStorageDTO userStoragetouserStorageDTO(org.advancedsr.entities.UserStorage userStorage);
    org.advancedsr.entities.UserStorage userStorageDTOtouserStorage(UserStorageDTO userStorageDTO);
}