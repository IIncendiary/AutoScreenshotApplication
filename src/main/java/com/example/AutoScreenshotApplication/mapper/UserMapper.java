package com.example.AutoScreenshotApplication.mapper;

import com.example.AutoScreenshotApplication.dto.UserDto;
import com.example.AutoScreenshotApplication.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(UserModel model);

    @Mapping(target = "archiveAttachments", ignore = true)
    UserModel toModel(UserDto dto);
    @Mapping(target = "archiveAttachments", ignore = true)
    void updateEntityFromDto(UserDto dto, @MappingTarget UserModel entity);
}

