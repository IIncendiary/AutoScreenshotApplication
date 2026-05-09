package com.example.AutoScreenshotApplication.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ScreenshotData {
    private String name;
    private byte[] bytes;
}
