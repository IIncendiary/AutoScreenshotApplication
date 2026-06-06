package com.example.AutoScreenshotApplication.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ArchiveMetadata {
    private LocalDateTime creationTime;
    private String filePath;
}
