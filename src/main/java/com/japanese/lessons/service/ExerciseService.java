package com.japanese.lessons.service;

import com.japanese.lessons.dtos.MangaDetailsDTO;
import com.japanese.lessons.dtos.StructuredDataForExercisesDTO;
import com.japanese.lessons.dtos.request.QuestionAnswerDTO;
import com.japanese.lessons.models.ETargetType;
import com.japanese.lessons.models.Exercise;
import com.japanese.lessons.models.Ordered_objects;
import com.japanese.lessons.models.lesson.exercise.Question;
import com.japanese.lessons.models.lesson.mangaExercise.Text;
import com.japanese.lessons.repositories.IExerciseRepository;
import com.japanese.lessons.service.Lesson.MangaDialogueService;
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
    @Autowired private MangaDialogueService mangaDialogueService;

    private Exercise getExerciseByLessonId(Long lessonId) {
        return iExerciseRepository.findByLessonId(lessonId).orElseThrow(() -> new IllegalArgumentException("Exercise is not found"));
    }

    private void addQuestion(Ordered_objects object ,List<StructuredDataForExercisesDTO> exercisesToReturn) {
        Question question = questionService.getQuestionById(object.getObjectId());
        List<Text> answerList = mangaDialogueService.getAllTextByTypeAndObjectId(ETargetType.QUESTION_TABLE, question.getId());
        QuestionAnswerDTO questionAnswerDTO = new QuestionAnswerDTO(question, answerList);
        logger.debug("Get question: {}", question);
        exercisesToReturn.add(new StructuredDataForExercisesDTO("question", questionAnswerDTO));
    }

    private void addSelect(Ordered_objects object ,List<StructuredDataForExercisesDTO> exercisesToReturn) {
        Question questionSelect = questionService.getQuestionById(object.getObjectId());
        List<Text> answerList = mangaDialogueService.getAllTextByTypeAndObjectId(ETargetType.QUESTION_TABLE, questionSelect.getId());
        QuestionAnswerDTO questionAnswerDTO = new QuestionAnswerDTO(questionSelect, answerList);
        exercisesToReturn.add(new StructuredDataForExercisesDTO("question_select", questionAnswerDTO));
    }

    private void addColocate(Ordered_objects object ,List<StructuredDataForExercisesDTO> exercisesToReturn) {
        logger.debug("add colocate with id: {}", object.getId());
        Question questionColocate = questionService.getQuestionById(object.getObjectId());
        List<Text> answerList = mangaDialogueService.getAllTextByTypeAndObjectId(ETargetType.QUESTION_TABLE, questionColocate.getId());
        QuestionAnswerDTO questionAnswerDTO = new QuestionAnswerDTO(questionColocate, answerList);
        exercisesToReturn.add(new StructuredDataForExercisesDTO("question_colocate", questionAnswerDTO));
    }

    private void addFact(Ordered_objects object ,List<StructuredDataForExercisesDTO> exercisesToReturn) {

    }

    public List<StructuredDataForExercisesDTO> getExercisesFromAllTables (Long lessonId) {
        logger.debug("Getting exercises for lesson ID: {}", lessonId);
        List<StructuredDataForExercisesDTO> exercisesToReturn = new ArrayList<>();
        Exercise exercise = getExerciseByLessonId(lessonId);
        MangaDetailsDTO exerciseDetails = new MangaDetailsDTO(exercise.getId(),exercise.getTopic(), exercise.getDescription());
        exercisesToReturn.add(new StructuredDataForExercisesDTO("details", exerciseDetails));
        List<Ordered_objects> orderedObjectsList = orderedObjectsService.getOrderedObjectsListByOrderedIdAndType(ETargetType.EXERCISE_TABLE, exercise.getId());
        logger.debug("by id: {}", exercise.getId());
        for (Ordered_objects index : orderedObjectsList) {
            logger.debug("Index: {}", index.toString());
            switch (index.getTargetType()) {
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
