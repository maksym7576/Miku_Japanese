package com.japanese.lessons.controller.Lesson;

import com.japanese.lessons.dtos.MangaContentDTO;
import com.japanese.lessons.dtos.StructuredDataForExercisesDTO;
import com.japanese.lessons.dtos.request.AnswersDTO;
import com.japanese.lessons.dtos.response.QuizRewardsDTO;
import com.japanese.lessons.models.lesson.mangaExercise.IncompleteMangaDataException;
import com.japanese.lessons.models.Manga;
import com.japanese.lessons.service.Lesson.MangaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/manga")
public class MangaController {

    @Autowired
    MangaService mangaService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getMangaById(@PathVariable Long id) {
        try {
            Manga manga = mangaService.getMangaById(id);
            return ResponseEntity.ok(manga);
        } catch (IncompleteMangaDataException e) {
            return ResponseEntity.ok("Partial data found: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Manga not found with id: " + id);
        }
    }

    @GetMapping(path = "/sorted/{id}")
    public ResponseEntity<?> getMangaByIDSortedByQueue(@PathVariable Long id) {
        try {
            List<StructuredDataForExercisesDTO> mangaContentDTOList = mangaService.sortMangaByQueue(id);
            return ResponseEntity.status(HttpStatus.OK).body(mangaContentDTOList);
        } catch (IncompleteMangaDataException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @PostMapping(path = "/finish")
    public ResponseEntity<?> returnResultWithCreatingIncorrectAnswers(@RequestBody List<AnswersDTO> answersDTO, @RequestParam Long userId, @RequestParam Long mangaId) {
        try {

            List<QuizRewardsDTO> rewards = mangaService.concludeManga(answersDTO, userId, mangaId);

            return ResponseEntity.ok(rewards);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred: " + e.getMessage());
        }
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
