package com.example.AutoScreenshotApplication;

import lombok.Data;

@Data
public class UserDto {
    private String name;

    private Long id;

    private String email;

    private UserSex sex;
}
