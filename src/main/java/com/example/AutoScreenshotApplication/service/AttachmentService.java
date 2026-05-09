package com.example.AutoScreenshotApplication.service;


import com.example.AutoScreenshotApplication.model.ArchiveMetadata;
import com.example.AutoScreenshotApplication.model.Attachment;
import com.example.AutoScreenshotApplication.repository.AttachmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;

@RequiredArgsConstructor
@Service
public class AttachmentService {
    private final AttachmentRepository attachmentRepository;


    public void createAttachment(ArchiveMetadata archiveMetadata) {
        Attachment attachment = new Attachment();
        File file = new File(archiveMetadata.getFilePath());
        attachment.setFileName(file.getName());
        attachment.setCreationTime(archiveMetadata.getCreationTime());
        attachmentRepository.save(attachment);
    }
}
