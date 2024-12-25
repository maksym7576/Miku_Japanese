package com.japanese.lessons.service;

import com.japanese.lessons.dtos.*;
import com.japanese.lessons.dtos.guidance.ExplanationWithTableDTO;
import com.japanese.lessons.dtos.request.FinalExerciseRequestDTO;
import com.japanese.lessons.dtos.response.FinalExerciseResponseDTO;
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
import java.util.stream.Collectors;

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
    @Autowired private RewardsService rewardsService;
    private Exercise getExerciseByEExerciseTypeAndLessonId(EExerciseType eExerciseType, Long lessonId) {
        List<Exercise> exerciseList = iExerciseRepository.findExercisesByLessonAndType(lessonId, eExerciseType);
        return exerciseList.get(0);
    }

    private Exercise getExerciseById(Long id) {
        return iExerciseRepository.getById(id);
    }

    private void addQuestion(Ordered_objects object , List<StructuredDataForExercisesDTO> exercisesToReturn) {
        ObjectWithMediaDTO objectWithMediaDTO = questionService.addMediaToQuestionAndGetQuestion(object.getActivityId(), "QUESTION");
        exercisesToReturn.add(new StructuredDataForExercisesDTO("question", objectWithMediaDTO));
    }

    private void addColocate(Ordered_objects object ,List<StructuredDataForExercisesDTO> exercisesToReturn) {
        ObjectWithMediaDTO objectWithMediaDTO = questionService.addMediaToQuestionAndGetQuestion(object.getActivityId(), "COLOCATE");
        exercisesToReturn.add(new StructuredDataForExercisesDTO("exercise_colocate", objectWithMediaDTO));
    }

    private void addFact(Ordered_objects object ,List<StructuredDataForExercisesDTO> exercisesToReturn) {
        ObjectWithMediaDTO objectWithMediaDTO = guidanceService.getFactWithMedia(object.getActivityId());
        exercisesToReturn.add(new StructuredDataForExercisesDTO("fact", objectWithMediaDTO));
    }

    private void addStudyContent(Ordered_objects object , List<StructuredDataForExercisesDTO> exercisesToReturn) {
        ExplanationWithTableDTO explanationWithTableDTO = guidanceService.getExplanationWithTable(object.getActivityId());
        exercisesToReturn.add(new StructuredDataForExercisesDTO("explanation", explanationWithTableDTO));
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

    private void addErrorCorrection(Ordered_objects object ,List<StructuredDataForExercisesDTO> exercisesToReturn) {
        ColocateWordsWithCorrectWordsListDTO correctWordsListDTO = questionService.addErrorCorrection(object.getActivityId());
        exercisesToReturn.add(new StructuredDataForExercisesDTO("colocate_with_finding_errors", correctWordsListDTO));
    }

    private void addQuestionInEnglishAnswers(Ordered_objects object, List<StructuredDataForExercisesDTO> exerciseToReturn) {
        ObjectWithMediaDTO objectWithMediaDTO = questionService.addMediaToQuestionAndGetQuestion(object.getActivityId(), "QUESTION_ENGLISH");
        exerciseToReturn.add(new StructuredDataForExercisesDTO("question_with_english_answers", objectWithMediaDTO));
    }

    private void addQuestionChooseAnswers(Ordered_objects object, List<StructuredDataForExercisesDTO> exerciseToReturn) {
        QuestionChooseDTO questionChooseDTO = questionService.getQuestionChoose(object.getActivityId());
        exerciseToReturn.add(new StructuredDataForExercisesDTO("question_choose", questionChooseDTO));
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
                case FACT ->  addFact(index, exercisesToReturn);
                case QUESTION_ENGLISH_ANSWERS -> addQuestionInEnglishAnswers(index, exercisesToReturn);
                case QUESTION_CHOOSE -> addQuestionChooseAnswers(index, exercisesToReturn);
//                case EXERCISE_MULTIPLE_ANSWER_QUESTION -> addMultipleAnswerQuestion(index, exercisesToReturn);
                case STUDY_CONTENT -> addStudyContent(index, exercisesToReturn);
                case PHRASE -> addFlashCardPopupPhrases(index, exercisesToReturn);
                case ERROR_CORRECTION -> addErrorCorrection(index, exercisesToReturn);
                default -> {}
            }
        }
        return exercisesToReturn;
    }

    public FinalExerciseResponseDTO concludeExercise(FinalExerciseRequestDTO finalExerciseRequestDTO) {
        List<Long> incorrectIds = new ArrayList<>();
        for (UserResponseDTO userResponseDTO : finalExerciseRequestDTO.getUserResponsesList()) {
            logger.debug("Is correct: {}", userResponseDTO.getIsCorrect());
            if(!userResponseDTO.getIsCorrect()) {
                logger.debug("Add incorrect id and status: {}", userResponseDTO.getQuestionId());
                incorrectIds.add(userResponseDTO.getQuestionId());
            }
        }
        logger.debug("Total responses: {}", finalExerciseRequestDTO.getUserResponsesList().size());
        logger.debug("Incorrect answers: {}", incorrectIds.size());
        int numCorrectAnswers = finalExerciseRequestDTO.getUserResponsesList().size() - incorrectIds.size();
        logger.debug("Correct answers: {}", numCorrectAnswers);
        int percentage = cuntPercentage(numCorrectAnswers, incorrectIds.size());
        logger.debug("Percentage: {}", percentage);
        int exp = countExperience(numCorrectAnswers, percentage);
        logger.debug("Exp: {}", exp);
        Exercise exercise = getExerciseById(finalExerciseRequestDTO.getExerciseId());
        return new FinalExerciseResponseDTO(percentage, exp, rewardsService.getRewardsDTOListByIdsList(exercise.getIdsRewards()));
    }

    private int cuntPercentage(int correct,int incorrect) {
        int sum = correct + incorrect;
        if(sum == 0) return 0;
        double percentage = (double) correct / sum * 100;
        return (int) percentage;
    }
    private int countExperience(int numCorrectAnswer, int percentage) {
        double sum = numCorrectAnswer * 2;
        double experience = sum * (percentage / 100.0);
        return (int) Math.floor(experience);
    }

}
