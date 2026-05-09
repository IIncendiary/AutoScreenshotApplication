package com.example.AutoScreenshotApplication.repository;

import com.example.AutoScreenshotApplication.model.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

}
