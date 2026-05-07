package com.example.AutoScreenshotApplication.Model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ScreenshotData {
    private String name;
    private byte[] bytes;
}
