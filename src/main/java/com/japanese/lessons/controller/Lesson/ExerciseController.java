package com.japanese.lessons.controller.Lesson;

import com.japanese.lessons.dtos.StructuredDataForExercisesDTO;
import com.japanese.lessons.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/api/exercise")
public class ExerciseController {

    @Autowired private ExerciseService exerciseService;

    @GetMapping(path = "/structured/{lesson_id}")
    public ResponseEntity<List<StructuredDataForExercisesDTO>> getAllStructuredExerciseDataByLessonId(@PathVariable Long lesson_id) {
        try {
            List<StructuredDataForExercisesDTO> result = exerciseService.getExercisesFromAllTables(lesson_id);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }
    }
}
