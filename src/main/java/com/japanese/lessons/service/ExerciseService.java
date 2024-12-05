package com.japanese.lessons.service;

import com.japanese.lessons.dtos.*;
import com.japanese.lessons.dtos.guidance.ExplanationWithTableDTO;
import com.japanese.lessons.dtos.response.models.ExerciseDTO;
import com.japanese.lessons.models.second.EExerciseType;
import com.japanese.lessons.models.second.Exercise;
import com.japanese.lessons.models.third.Ordered_objects;
import com.japanese.lessons.repositories.IExerciseRepository;
import com.japanese.lessons.service.Lesson.MangaDialogueService;
import com.japanese.lessons.service.Lesson.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ExerciseService {
    private static final Logger logger = LoggerFactory.getLogger(ExerciseService.class);
    @Autowired private IExerciseRepository iExerciseRepository;
    @Autowired private QuestionService questionService;
    @Autowired private Ordered_objects_service orderedObjectsService;
    @Autowired private MangaDialogueService mangaDialogueService;
    @Autowired private GuidanceService guidanceService;
    @Autowired private FileRecordService fileRecordService;
    @Autowired private PhraseService phraseService;
    private Exercise getExerciseByEExerciseTypeAndLessonId(EExerciseType eExerciseType, Long lessonId) {
        List<Exercise> exerciseList = iExerciseRepository.findExercisesByLessonAndType(lessonId, eExerciseType);
        return exerciseList.get(0);
    }

    private void addQuestion(Ordered_objects object , List<StructuredDataForExercisesDTO> exercisesToReturn) {
        ObjectWithMediaDTO objectWithMediaDTO = questionService.addMediaToQuestion(object.getActivityId(), "QUESTION");
        exercisesToReturn.add(new StructuredDataForExercisesDTO("question", objectWithMediaDTO));
    }

    private void addColocate(Ordered_objects object ,List<StructuredDataForExercisesDTO> exercisesToReturn) {
        ObjectWithMediaDTO objectWithMediaDTO = questionService.addMediaToQuestion(object.getActivityId(), "COLOCATE");
        exercisesToReturn.add(new StructuredDataForExercisesDTO("exercise_colocate", objectWithMediaDTO));
    }

    private void addFact(Ordered_objects object ,List<StructuredDataForExercisesDTO> exercisesToReturn) {

    }

    private void addStudyContentNewWords(Ordered_objects object , List<StructuredDataForExercisesDTO> exercisesToReturn) {
        ExplanationWithTableDTO explanationWithTableDTO = guidanceService.getExplanationWithTable(object.getActivityId());
        exercisesToReturn.add(new StructuredDataForExercisesDTO("explanation_with_table", explanationWithTableDTO));
    }

    private void addStudyContentPhrases(Ordered_objects object , List<StructuredDataForExercisesDTO> exercisesToReturn) {
        ExplanationWithTableDTO explanationWithPhrasesTableDTO = guidanceService.getExplanationWithTable(object.getActivityId());
        exercisesToReturn.add(new StructuredDataForExercisesDTO("explanation_with_phrases", explanationWithPhrasesTableDTO));
    }

    private void addMultipleAnswerQuestion(Ordered_objects object ,List<StructuredDataForExercisesDTO> exercisesToReturn) {
//        logger.debug("add colocate with id: {}", object.getId());
//        Question questionColocate = questionService.getQuestionById(object.getActivityId());
//        List<Text> answerList = mangaDialogueService.getAllTextByTypeAndObjectId(ETargetType.QUESTION_TABLE, questionColocate.getId());
//        QuestionAnswerDTO questionAnswerDTO = new QuestionAnswerDTO(questionColocate, answerList, null);
//        exercisesToReturn.add(new StructuredDataForExercisesDTO("question_multiple_answer", questionAnswerDTO));
    }

    private void addFlashCardPopupPhrases(Ordered_objects object ,List<StructuredDataForExercisesDTO> exercisesToReturn) {
        ObjectWithMediaDTO objectWithMediaDTO = phraseService.getPhraseWithMedia(object.getActivityId());
        exercisesToReturn.add(new StructuredDataForExercisesDTO("flash_card_popup", objectWithMediaDTO));
    }

    public List<StructuredDataForExercisesDTO> getExercisesFromAllTables (Long lessonId) {
        logger.debug("Getting exercises for lesson ID: {}", lessonId);
        List<StructuredDataForExercisesDTO> exercisesToReturn = new ArrayList<>();
        Exercise exercise = getExerciseByEExerciseTypeAndLessonId(EExerciseType.EXERCISE, lessonId);
        ExerciseDTO exerciseDetails = new ExerciseDTO(exercise.getId(),exercise.getTopic(), exercise.getDescription());
        exercisesToReturn.add(new StructuredDataForExercisesDTO("details", exerciseDetails));
        List<Ordered_objects> orderedObjectsList = orderedObjectsService.getOrderedObjectsListByOrderedIdAndType(exercise.getId());
        orderedObjectsList.sort(Comparator.comparing(Ordered_objects:: getOrderIndex));
        logger.debug("by id: {}", exercise.getId());
        for (Ordered_objects index : orderedObjectsList) {
            logger.debug("Index: {}", index.toString());
            switch (index.getActivityType()) {
                case QUESTION -> addQuestion(index, exercisesToReturn);
                case QUESTION_COLOCATE -> addColocate(index, exercisesToReturn);
//                case EXERCISE_FACT ->  addFact(index, exercisesToReturn);
//                case EXERCISE_MULTIPLE_ANSWER_QUESTION -> addMultipleAnswerQuestion(index, exercisesToReturn);
                case STUDY_CONTENT_WORDS -> addStudyContentNewWords(index, exercisesToReturn);
                case STUDY_CONTENT_PHRASES -> addStudyContentPhrases(index, exercisesToReturn);
                case FLASHCARD_POPUP -> addFlashCardPopupPhrases(index, exercisesToReturn);
                default -> {}
            }
        }
        return exercisesToReturn;
    }
}
