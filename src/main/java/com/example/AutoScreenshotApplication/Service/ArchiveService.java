package com.example.AutoScreenshotApplication.Service;

import com.example.AutoScreenshotApplication.Utility.ApplicationConstants;
import com.example.AutoScreenshotApplication.Model.ArchiveMetadata;
import com.example.AutoScreenshotApplication.Model.ScreenshotData;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class ArchiveService {
    public ArchiveMetadata createArchive(List<ScreenshotData> screenShots) {
        ArchiveMetadata archiveMetadata = new ArchiveMetadata();
        new File(ApplicationConstants.ARCHIVE_DIR).mkdirs();
        String zipPath = String.format("%s/Archive#_%d.zip", ApplicationConstants.ARCHIVE_DIR, System.currentTimeMillis());
        try (FileOutputStream fileOutputStream = new FileOutputStream(zipPath); ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream)) {
            for (ScreenshotData entry : screenShots) {
                zipOutputStream.putNextEntry(new ZipEntry(entry.getName()));
                zipOutputStream.write(entry.getBytes());
                zipOutputStream.closeEntry();
            }
            archiveMetadata.setCreationTime(LocalDateTime.now());
            archiveMetadata.setFilePath(zipPath);
            return archiveMetadata;
        } catch (Exception e) {
            throw new RuntimeException("Cannot create an archive", e);
        }

    }
}
