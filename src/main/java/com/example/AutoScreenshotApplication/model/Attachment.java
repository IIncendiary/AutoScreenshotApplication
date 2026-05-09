package com.example.AutoScreenshotApplication.model;


import jakarta.persistence.*;
import lombok.*;
import org.apache.catalina.User;

import java.time.LocalDateTime;
import java.util.ArrayList;

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
    int id;

    @Column(name = "storage_path")
    String storagePath;

    @Column(name = "file_name")
    String fileName;

    @Column(name = "size")
    long size;

    @Column(name = "creation_time")
    LocalDateTime creationTime;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserModel owner;
}
