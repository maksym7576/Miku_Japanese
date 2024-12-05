package com.japanese.lessons.controller;

import com.japanese.lessons.service.ExerciseService;
import com.japanese.lessons.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@Controller
@RequestMapping("/api/url")
@RequiredArgsConstructor
public class FileUploadController {

    private final FileUploadService fileUploadService;
    private static final Logger logger = LoggerFactory.getLogger(ExerciseService.class);

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        try {
            fileUploadService.uploadFile(multipartFile);
            return ResponseEntity.status(HttpStatus.OK).body("Saved successful");
        } catch (IOException e) {
            logger.error("Error occurred while uploading the file: ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Has occurred an error");
        }
    }

}
