package com.japanese.lessons.service;

import com.cloudinary.Cloudinary;
import com.japanese.lessons.models.EFileURLType;
import com.japanese.lessons.models.FileRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
public class FileUploadService {

    @Autowired private Cloudinary cloudinary;
    @Autowired private FileRecordService fileRecordService;


    public void uploadFile(MultipartFile multipartFile) throws IOException {
        String contentType = multipartFile.getContentType();
        FileRecords fileRecords = new FileRecords();

        if (contentType != null && contentType.startsWith("video/")) {
            fileRecords.setFileURLType(EFileURLType.video);
        } else if (contentType != null && contentType.startsWith("image/")) {
            fileRecords.setFileURLType(EFileURLType.image);
        } else {
            throw new IllegalArgumentException("Unsupported file type. Please upload an image or video.");
        }

        Map<String, Object> uploadResult = cloudinary.uploader()
                .upload(multipartFile.getBytes(),
                Map.of("resource_type", fileRecords.getFileURLType().toString(),
                           "public_id", UUID.randomUUID().toString()
                ));

        fileRecords.setUrl(uploadResult.get("url").toString());
        FileRecords fileRecordsSaved = fileRecordService.saveFileRecords(fileRecords);
    }

}
