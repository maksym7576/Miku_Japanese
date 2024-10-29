package com.japanese.lessons.controller.Lesson;

import com.japanese.lessons.models.lesson.mangaExercise.Manga;
import com.japanese.lessons.service.Lesson.MangaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/manga")
public class MangaController {

    @Autowired
    MangaService mangaService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<Manga> getMangaById(@PathVariable Long id) {
        Manga manga = mangaService.getMangaById(id);
        return ResponseEntity.ok(manga);
    }
    @PostMapping(path = "/create")
    public ResponseEntity<String> createManga(@RequestBody Manga manga) {
        try {
            mangaService.saveManga(manga);
            return ResponseEntity.status(HttpStatus.CREATED).body("Manga created successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error during manga creation process: " + e.getMessage());
        }
    }
}
