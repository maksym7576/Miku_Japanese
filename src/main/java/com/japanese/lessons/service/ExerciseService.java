package com.japanese.lessons.service;

import com.japanese.lessons.dtos.MangaDetailsDTO;
import com.japanese.lessons.dtos.StructuredDataForExercisesDTO;
import com.japanese.lessons.dtos.TypeObjectDTO;
import com.japanese.lessons.models.Exercise;
import com.japanese.lessons.models.lesson.exercise.Question;
import com.japanese.lessons.repositories.IExerciseRepository;
import com.japanese.lessons.service.Lesson.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExerciseService {

    @Autowired private IExerciseRepository iExerciseRepository;
    @Autowired private QuestionService questionService;

    private Exercise getExerciseByLessonId(Long lessonId) {
        return iExerciseRepository.findByLessonId(lessonId).orElseThrow(() -> new IllegalArgumentException("Exercise is not found"));
    }

    private void addQuestion(TypeObjectDTO object ,List<StructuredDataForExercisesDTO> exercisesToReturn) {
        Question question = questionService.getQuestionById(object.getObjectId());
        exercisesToReturn.add(new StructuredDataForExercisesDTO("question", question));
    }

    private void addSelect(TypeObjectDTO object ,List<StructuredDataForExercisesDTO> exercisesToReturn) {
        Question questionSelect = questionService.getQuestionById(object.getObjectId());
        exercisesToReturn.add(new StructuredDataForExercisesDTO("question_select", questionSelect));
    }

    private void addColocate(TypeObjectDTO object ,List<StructuredDataForExercisesDTO> exercisesToReturn) {

    }

    private void addFact(TypeObjectDTO object ,List<StructuredDataForExercisesDTO> exercisesToReturn) {

    }

    private List<StructuredDataForExercisesDTO> getExercisesFromAllTables (Long lessonId) {
    List<StructuredDataForExercisesDTO> exercisesToReturn = new ArrayList<>();
    Exercise exercise = getExerciseByLessonId(lessonId);
    MangaDetailsDTO exerciseDetails = new MangaDetailsDTO(exercise.getId(),exercise.getTopic(), exercise.getDescription());
    exercisesToReturn.add(new StructuredDataForExercisesDTO("details", exerciseDetails));
    for (TypeObjectDTO object : exercise.getObjectList()) {
        switch (object.getObjectType()) {
            case EXERCISE_QUESTION -> addQuestion(object, exercisesToReturn);
            case EXERCISE_SELECT -> addSelect(object, exercisesToReturn);
            case EXERCISE_COLOCATE -> addColocate(object, exercisesToReturn);
            case EXERCISE_FACT ->  addFact(object, exercisesToReturn);
            default -> new IllegalArgumentException("This object is not exists: " + object);
        }
    }
    return exercisesToReturn;
    }
}
