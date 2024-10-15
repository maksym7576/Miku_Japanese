package com.japanese.lessons.controller;

import com.japanese.lessons.models.LessonMicroservice.manga.Manga;
import com.japanese.lessons.service.LessonMicroservice.manga.MangaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
