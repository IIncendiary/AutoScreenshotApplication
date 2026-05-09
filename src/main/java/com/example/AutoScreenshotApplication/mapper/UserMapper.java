package com.example.AutoScreenshotApplication.mapper;

import com.example.AutoScreenshotApplication.dto.UserDto;
import com.example.AutoScreenshotApplication.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(UserModel model);

    UserModel toModel(UserDto dto);

    void updateEntityFromDto(UserDto dto, @MappingTarget UserModel entity);
}

