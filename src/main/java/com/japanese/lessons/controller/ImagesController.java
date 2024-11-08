package com.japanese.lessons.controller;

import com.japanese.lessons.models.lesson.mangaExercise.Image;
import com.japanese.lessons.service.ImagesService;
import com.japanese.lessons.service.Lesson.MangaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/api/images")
public class ImagesController {

    @Autowired
    ImagesService imagesService;

    @Autowired
    MangaService mangaService;

//    @PostMapping("/create/{mangaId}")
//    public ResponseEntity<?> uploadFlightImage(
//            @PathVariable Long mangaId,
//            @RequestParam int turn,
//            @RequestParam("file") MultipartFile file) {
//        try {
//            if (file.isEmpty()) {
//                return ResponseEntity.badRequest().body("File cannot be empty.");
//            }
//
//            imagesService.uploadImage(mangaId, turn, file);
//            return ResponseEntity.ok("Image uploaded successfully.");
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("Error saving image: " + e.getMessage());
//        }
//    }


}
