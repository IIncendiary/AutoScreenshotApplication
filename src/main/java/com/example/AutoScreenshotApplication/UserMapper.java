package com.example.AutoScreenshotApplication;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(UserModel model);

    UserModel toModel(UserDto dto);

    void updateEntityFromDto(UserDto dto, @MappingTarget UserModel entity);
}

