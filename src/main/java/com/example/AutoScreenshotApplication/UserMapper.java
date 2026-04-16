package com.example.AutoScreenshotApplication;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(UserModel model);

    @Mapping(target = "id", ignore = true)
    UserModel toModel(UserDto dto);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(UserDto dto, @MappingTarget UserModel entity);
}

