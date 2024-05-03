package org.advancedsr.mappers;

import org.advancedsr.entities.Image;
import org.advancedsr.dtos.ImageDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ImageMapper {
    ImageMapper INSTANCE = Mappers.getMapper(ImageMapper.class);

    @Mapping(source = "user.username", target = "username")
    ImageDTO imageToImageDTO(Image image);

    @Mapping(source = "username", target = "user.username")
    Image imageDTOToImage(ImageDTO imageDTO);
}
