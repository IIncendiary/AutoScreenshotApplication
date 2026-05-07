package com.example.AutoScreenshotApplication;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "archive_attachment")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int archiveId;

    @Column(nullable = false, length = 100)
    String storagePath;

    @Column(nullable = false, length = 100)
    String fileName;

    @Column(nullable = false)
    long size;

    @Column(nullable = false, updatable = false)
    LocalDateTime creationTime;
}
