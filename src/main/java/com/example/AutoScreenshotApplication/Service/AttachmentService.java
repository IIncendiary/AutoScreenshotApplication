package com.example.AutoScreenshotApplication.Service;


import com.example.AutoScreenshotApplication.Model.ArchiveMetadata;
import com.example.AutoScreenshotApplication.Model.Attachment;
import com.example.AutoScreenshotApplication.Repository.AttachmentRepository;
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
