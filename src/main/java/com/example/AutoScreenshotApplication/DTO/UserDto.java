package com.example.AutoScreenshotApplication.DTO;

import com.example.AutoScreenshotApplication.Utility.UserSex;
import lombok.Data;

@Data
public class UserDto {
    private String name;

    private Long id;

    private String email;

    private UserSex sex;
}
