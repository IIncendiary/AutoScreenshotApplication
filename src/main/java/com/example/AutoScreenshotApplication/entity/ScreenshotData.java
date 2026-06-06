package com.example.AutoScreenshotApplication.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ScreenshotData {
    private String name;
    private byte[] bytes;
}
