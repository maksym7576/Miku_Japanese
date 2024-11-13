package com.japanese.lessons.service;

import com.japanese.lessons.dtos.MangaDetailsDTO;
import com.japanese.lessons.dtos.StructuredDataForExercisesDTO;
import com.japanese.lessons.models.ETargetType;
import com.japanese.lessons.models.Exercise;
import com.japanese.lessons.models.Ordered_objects;
import com.japanese.lessons.models.lesson.exercise.Question;
import com.japanese.lessons.repositories.IExerciseRepository;
import com.japanese.lessons.service.Lesson.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExerciseService {
    private static final Logger logger = LoggerFactory.getLogger(ExerciseService.class);
    @Autowired private IExerciseRepository iExerciseRepository;
    @Autowired private QuestionService questionService;
    @Autowired private Ordered_objects_service orderedObjectsService;

    private Exercise getExerciseByLessonId(Long lessonId) {
        return iExerciseRepository.findByLessonId(lessonId).orElseThrow(() -> new IllegalArgumentException("Exercise is not found"));
    }

    private void addQuestion(Ordered_objects object ,List<StructuredDataForExercisesDTO> exercisesToReturn) {
        Question question = questionService.getQuestionById(object.getObjectId());
        logger.debug("Get question: {}", question);
        exercisesToReturn.add(new StructuredDataForExercisesDTO("question", question));
    }

    private void addSelect(Ordered_objects object ,List<StructuredDataForExercisesDTO> exercisesToReturn) {
        Question questionSelect = questionService.getQuestionById(object.getObjectId());
        exercisesToReturn.add(new StructuredDataForExercisesDTO("question_select", questionSelect));
    }

    private void addColocate(Ordered_objects object ,List<StructuredDataForExercisesDTO> exercisesToReturn) {
        Question questionColocate = questionService.getQuestionById(object.getObjectId());
        exercisesToReturn.add(new StructuredDataForExercisesDTO("question_colocate", questionColocate));
    }

    private void addFact(Ordered_objects object ,List<StructuredDataForExercisesDTO> exercisesToReturn) {

    }

    public List<StructuredDataForExercisesDTO> getExercisesFromAllTables (Long lessonId) {
        logger.debug("Getting exercises for lesson ID: {}", lessonId);
        List<StructuredDataForExercisesDTO> exercisesToReturn = new ArrayList<>();
        Exercise exercise = getExerciseByLessonId(lessonId);
        MangaDetailsDTO exerciseDetails = new MangaDetailsDTO(exercise.getId(),exercise.getTopic(), exercise.getDescription());
        exercisesToReturn.add(new StructuredDataForExercisesDTO("details", exerciseDetails));
        List<Ordered_objects> orderedObjectsList = orderedObjectsService.getOrderedObjectsListByOrderedIdAndType(ETargetType.EXERCISE_QUESTION, exercise.getId());
        logger.debug("obtained orderList: {}", orderedObjectsList);
        logger.debug("by id: {}", exercise.getId());
        for (Ordered_objects index : orderedObjectsList) {
            logger.debug("Index: {}", index);
            switch (index.getTargetType()) {  // тепер використовуємо targetType
                case EXERCISE_QUESTION -> addQuestion(index, exercisesToReturn);
                case EXERCISE_SELECT -> addSelect(index, exercisesToReturn);
                case EXERCISE_COLOCATE -> addColocate(index, exercisesToReturn);
                case EXERCISE_FACT ->  addFact(index, exercisesToReturn);
                default -> {}
            }
        }
        return exercisesToReturn;
    }
}
