package com.japanese.lessons.controller.Lesson;

import com.japanese.lessons.models.Lesson.Lesson;
import com.japanese.lessons.service.Lesson.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
