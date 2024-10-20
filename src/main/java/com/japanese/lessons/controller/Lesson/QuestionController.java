package com.japanese.lessons.controller.Lesson;

import com.japanese.lessons.models.Lesson.Question;
import com.japanese.lessons.service.Lesson.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<String> updateQuestion(@PathVariable Long id, @RequestBody Question question) {
        try {
            questionService.updateQuestion(id, question);
            return ResponseEntity.status(HttpStatus.OK).body("Question updated successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable Long id) {
        try {
            questionService.deleteQuestion(id);
            return ResponseEntity.status(HttpStatus.OK).body("Question deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }
    @PostMapping(path = "/create")
    public ResponseEntity<String> createQuestion(@RequestBody Question question) {
    try {
        questionService.saveQuestion(question);
        return ResponseEntity.status(HttpStatus.CREATED).body("Question is created");
    } catch (IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
    }
    }
}
