package com.example.AutoScreenshotApplication.dto;

import com.example.AutoScreenshotApplication.utility.UserSex;
import lombok.Data;

@Data
public class UserDto {
    private String name;

    private Long id;

    private String email;

    private UserSex sex;
}
