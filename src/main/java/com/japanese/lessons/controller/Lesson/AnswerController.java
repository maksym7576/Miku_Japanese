package com.japanese.lessons.controller.Lesson;

import com.japanese.lessons.models.lesson.Answer;
import com.japanese.lessons.service.Lesson.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/answer")
public class AnswerController {

    @Autowired
    AnswerService answerService;

    @PostMapping(path = "/create")
    public ResponseEntity<String> createAnswer(@Validated @RequestBody Answer answer) {
        try {
            answerService.saveAnswer(answer);
            return ResponseEntity.status(HttpStatus.CREATED).body("Answer has been created");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<String> deleteAnswer(@PathVariable Long id) {
        try {
            answerService.deleteAnswer(id);
            return ResponseEntity.status(HttpStatus.OK).body("Answer has been deleted");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<String> updateAnswer(@PathVariable Long id, @RequestBody Answer answer) {
        try {
            answerService.updateAnswer(id, answer);
            return ResponseEntity.status(HttpStatus.OK).body("Answer has been updated");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }
}
