package com.example.AutoScreenshotApplication.mapper;

import com.example.AutoScreenshotApplication.dto.UserDto;
import com.example.AutoScreenshotApplication.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User model);

    @Mapping(target = "archiveAttachments", ignore = true)
    User toEntity(UserDto dto);
}

