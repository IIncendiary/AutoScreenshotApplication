package com.example.AutoScreenshotApplication.Repository;

import com.example.AutoScreenshotApplication.Model.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

}
