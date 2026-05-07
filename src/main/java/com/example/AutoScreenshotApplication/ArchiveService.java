package com.example.AutoScreenshotApplication;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class ArchiveService {
    ArchiveMetadata createArchive(List<ScreenshotData> screenShots) {
        ArchiveMetadata archiveMetadata = new ArchiveMetadata();
        new File(ApplicationConstants.ARCHIVE_DIR).mkdirs();
        String zipPath = String.format("%s/Archive#_%d.zip", ApplicationConstants.ARCHIVE_DIR, System.currentTimeMillis());
        try (FileOutputStream fileOutputStream = new FileOutputStream(zipPath); ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream)) {
            for (ScreenshotData entry : screenShots) {
                zipOutputStream.putNextEntry(new ZipEntry(entry.name));
                zipOutputStream.write(entry.bytes);
                zipOutputStream.closeEntry();
            }
            archiveMetadata.creationTime = LocalDateTime.now();
            archiveMetadata.filePath = zipPath;
            return archiveMetadata;
        } catch (Exception e) {
            throw new RuntimeException("Cannot create an archive", e);
        }

    }
}
