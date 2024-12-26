package com.japanese.lessons.controller.Lesson;

import com.japanese.lessons.dtos.LessonDetailsDTO;
import com.japanese.lessons.dtos.StructuredDataForExercisesDTO;
import com.japanese.lessons.dtos.request.FinalExerciseRequestDTO;
import com.japanese.lessons.dtos.response.FinalExerciseResponseDTO;
import com.japanese.lessons.dtos.response.models.ExerciseDTO;
import com.japanese.lessons.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/api/exercise")
public class ExerciseController {

    @Autowired private ExerciseService exerciseService;

    @GetMapping(path = "/structured/{lesson_id}/test")
    public ResponseEntity<List<StructuredDataForExercisesDTO>> getAllStructuredExerciseDataByLessonId(@PathVariable Long lesson_id) {
        try {
            List<StructuredDataForExercisesDTO> result = exerciseService.getExercisesFromAllTables(lesson_id);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }
    }

    @PostMapping("/finish")
    public ResponseEntity<?> concludeUserResponses(@RequestBody FinalExerciseRequestDTO finalExerciseRequestDTO) {
        try {
            FinalExerciseResponseDTO result = exerciseService.concludeExercise(finalExerciseRequestDTO);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Has ocurred an error:" + e.getMessage());
        }
    }

    @GetMapping("/get/{lessonId}/user/{userId}")
    public ResponseEntity<?> getAllExercisesByLessonIdAndReviewIsFinished(@PathVariable Long lessonId, @PathVariable Long userId) {
        try {
            LessonDetailsDTO lessonDetailsDTO = exerciseService.getLessonDetailsByLessonIdAndUserId(lessonId, userId);
            return ResponseEntity.status(HttpStatus.OK).body(lessonDetailsDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Has occurred an error:" + e.getMessage());
        }
    }
}
