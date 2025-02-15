package com.japanese.lessons.controller.Lesson;

import com.japanese.lessons.dtos.response.models.LessonDTO;
import com.japanese.lessons.models.first.Lesson;
import com.japanese.lessons.service.Lesson.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/lesson")
public class LessonController {

    @Autowired
    LessonService lessonService;

    @PostMapping(path = "/create")
    public ResponseEntity<String> createLesson(@RequestBody Lesson lesson) {
        try {
            lessonService.saveLesson(lesson);
            return ResponseEntity.status(HttpStatus.CREATED).body("Lesson is created");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }
    @GetMapping(path = "/get")
    public ResponseEntity<?> getAllLessons() {
        try {
            List<Lesson> lessons = lessonService.getAllLessons();
            if (lessons.isEmpty()) {
                return ResponseEntity.ok()
                        .body(Map.of("message", "No lessons found", "data", lessons));
            }
            return ResponseEntity.ok(lessons);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to retrieve lessons",
                            "message", e.getMessage()));
        }
    }
    @GetMapping(path = "/get/sorted")
    public ResponseEntity<?> getAllSortedLessons() {
        try {
            List<Lesson> lessonList = lessonService.getAllSortedLessons();
            if (lessonList.isEmpty()) {
                return ResponseEntity.ok().body(Map.of("message", "No lessons found", "data", lessonList));
            }
            return ResponseEntity.ok(lessonList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Failed to retrieve lessons",
                    "message", e.getMessage()));
        }
    }
//    @GetMapping("/get/sorted/user/{userId}")
//    public ResponseEntity<?> getAllSortedLessonsAndFormIsFinishedByUSerId(@PathVariable Long userId) {
//        try {
//            List<LessonDTO> lessonDTOList = lessonService.getAllSortedLessonsAndWhatFinishedMarkFinished(userId);
//            return ResponseEntity.status(HttpStatus.OK).body(lessonDTOList);
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Has occurred an error:" + e.getMessage());
//        }
//    }
}
