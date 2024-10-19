package com.japanese.lessons.controller;

import com.japanese.lessons.models.LessonMicroservice.manga.MangaDialogue;
import com.japanese.lessons.service.LessonMicroservice.manga.MangaDialogueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/text")
public class MangaTextController {

    @Autowired
    MangaDialogueService mangaDialogueService;

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<String> deleteText(@PathVariable Long id) {
        try {
            mangaDialogueService.deleteMangaDialogue(id);
            return ResponseEntity.status(HttpStatus.OK).body("Text deleted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error during text deletion process: " + e.getMessage());
        }
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<String> updateText(@PathVariable Long id, @RequestBody MangaDialogue mangaDialogue) {
        try {
            mangaDialogueService.updateMangaDialogue(id, mangaDialogue);
            return ResponseEntity.status(HttpStatus.OK).body("Text updated successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }
}
