package com.example.AutoScreenshotApplication;

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
    LocalDateTime creationTime = LocalDateTime.now();
    String filePath;
}
