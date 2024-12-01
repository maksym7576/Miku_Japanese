package com.japanese.lessons.service;

import com.japanese.lessons.dtos.ExplanationWithTableDTO;
import com.japanese.lessons.dtos.MangaDetailsDTO;
import com.japanese.lessons.dtos.ObjectWithAudioDTO;
import com.japanese.lessons.dtos.StructuredDataForExercisesDTO;
import com.japanese.lessons.dtos.request.QuestionAnswerDTO;
import com.japanese.lessons.models.*;
import com.japanese.lessons.models.lesson.exercise.Question;
import com.japanese.lessons.models.lesson.mangaExercise.Text;
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
    @Autowired private VocabularyService vocabularyService;
    @Autowired private AudioService audioService;
    @Autowired private ImagesService imagesService;
    private Exercise getExerciseByLessonId(Long lessonId) {
        return iExerciseRepository.findByLessonId(lessonId).orElseThrow(() -> new IllegalArgumentException("Exercise is not found"));
    }

    private void addQuestion(Ordered_objects object ,List<StructuredDataForExercisesDTO> exercisesToReturn) {
        Question question = questionService.getQuestionById(object.getObjectId());
        List<Text> answerList = mangaDialogueService.getAllTextByTypeAndObjectId(ETargetType.QUESTION_TABLE, question.getId());
        List<Image> image = imagesService.getImagesByObjectId(ETargetType.QUESTION_TABLE, question.getId());
        QuestionAnswerDTO questionAnswerDTO = new QuestionAnswerDTO(question, answerList, image.get(0));
        logger.debug("Get question: {}", question);
        exercisesToReturn.add(new StructuredDataForExercisesDTO("question", questionAnswerDTO));
    }

    private void addColocate(Ordered_objects object ,List<StructuredDataForExercisesDTO> exercisesToReturn) {
        Question questionSelect = questionService.getQuestionById(object.getObjectId());
        List<Text> questioSelectWordsList = mangaDialogueService.getAllTextByTypeAndObjectId(ETargetType.QUESTION_TABLE, questionSelect.getId());
        Collections.shuffle(questioSelectWordsList);
        QuestionAnswerDTO questionAnswerDTO = new QuestionAnswerDTO(questionSelect, questioSelectWordsList, null);
        exercisesToReturn.add(new StructuredDataForExercisesDTO("question_select", questionAnswerDTO));

    }

    private void addFact(Ordered_objects object ,List<StructuredDataForExercisesDTO> exercisesToReturn) {

    }

    private void addStudyContentNewWords(Ordered_objects object , List<StructuredDataForExercisesDTO> exercisesToReturn) {
        ExplanationWithTableDTO explanationWithTableDTO = guidanceService.getExplanationWithTables(object.getObjectId());
        exercisesToReturn.add(new StructuredDataForExercisesDTO("explanation_with_table", explanationWithTableDTO));
    }

    private void addStudyContentPhrases(Ordered_objects object , List<StructuredDataForExercisesDTO> exercisesToReturn) {

    }

    private void addMultipleAnswerQuestion(Ordered_objects object ,List<StructuredDataForExercisesDTO> exercisesToReturn) {
        logger.debug("add colocate with id: {}", object.getId());
        Question questionColocate = questionService.getQuestionById(object.getObjectId());
        List<Text> answerList = mangaDialogueService.getAllTextByTypeAndObjectId(ETargetType.QUESTION_TABLE, questionColocate.getId());
        QuestionAnswerDTO questionAnswerDTO = new QuestionAnswerDTO(questionColocate, answerList, null);
        exercisesToReturn.add(new StructuredDataForExercisesDTO("question_multiple_answer", questionAnswerDTO));
    }

    private void addFlashCardPopup(Ordered_objects object ,List<StructuredDataForExercisesDTO> exercisesToReturn) {
        Vocabulary vocabulary = vocabularyService.getVocabularyById(object.getObjectId());
        ObjectWithAudioDTO objectWithAudioDTO = new ObjectWithAudioDTO();
        objectWithAudioDTO.setObject(vocabulary);
        Audio audio = audioService.getByTypeAndObjectId(ETargetType.VOCABULARY_TABLE, object.getObjectId());
        objectWithAudioDTO.setAudio(audio);
        List<Image> imageList = imagesService.getImagesByObjectId(ETargetType.FLASHCARD_POPUP, vocabulary.getId());
        objectWithAudioDTO.setImage(imageList.get(0));
        exercisesToReturn.add(new StructuredDataForExercisesDTO("flash_card_popup", objectWithAudioDTO));
    }

    public List<StructuredDataForExercisesDTO> getExercisesFromAllTables (Long lessonId) {
        logger.debug("Getting exercises for lesson ID: {}", lessonId);
        List<StructuredDataForExercisesDTO> exercisesToReturn = new ArrayList<>();
        Exercise exercise = getExerciseByLessonId(lessonId);
        MangaDetailsDTO exerciseDetails = new MangaDetailsDTO(exercise.getId(),exercise.getTopic(), exercise.getDescription());
        exercisesToReturn.add(new StructuredDataForExercisesDTO("details", exerciseDetails));
        List<Ordered_objects> orderedObjectsList = orderedObjectsService.getOrderedObjectsListByOrderedIdAndType(ETargetType.EXERCISE_TABLE, exercise.getId());
        orderedObjectsList.sort(Comparator.comparing(Ordered_objects:: getOrderIndex));
        logger.debug("by id: {}", exercise.getId());
        for (Ordered_objects index : orderedObjectsList) {
            logger.debug("Index: {}", index.toString());
            switch (index.getTargetType()) {
                case EXERCISE_QUESTION -> addQuestion(index, exercisesToReturn);
                case EXERCISE_COLOCATE -> addColocate(index, exercisesToReturn);
                case EXERCISE_FACT ->  addFact(index, exercisesToReturn);
                case EXERCISE_MULTIPLE_ANSWER_QUESTION -> addMultipleAnswerQuestion(index, exercisesToReturn);
                case FLASHCARD_POPUP -> addFlashCardPopup(index, exercisesToReturn);
                case STUDY_CONTENT_WORDS -> addStudyContentNewWords(index, exercisesToReturn);
                default -> {}
            }
        }
        return exercisesToReturn;
    }
}
