package com.japanese.lessons.service;

import com.japanese.lessons.dtos.*;
import com.japanese.lessons.dtos.guidance.ExplanationWithTableDTO;
import com.japanese.lessons.dtos.request.FinalExerciseRequestDTO;
import com.japanese.lessons.dtos.response.FinalExerciseResponseDTO;
import com.japanese.lessons.dtos.response.models.*;
import com.japanese.lessons.models.User.EFinishedTypes;
import com.japanese.lessons.models.User.User;
import com.japanese.lessons.models.User.UserProgress;
import com.japanese.lessons.models.second.EExerciseType;
import com.japanese.lessons.models.second.Exercise;
import com.japanese.lessons.models.sixsth.FileRecords;
import com.japanese.lessons.models.third.EActivityType;
import com.japanese.lessons.models.third.Ordered_objects;
import com.japanese.lessons.repositories.IExerciseRepository;
import com.japanese.lessons.service.Lesson.MangaDialogueService;
import com.japanese.lessons.service.Lesson.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
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
    @Autowired private UserProgressService userProgressService;
    @Autowired private SubtitleService subtitleService;
    @Autowired private UserService userService;

    private Exercise getExerciseByEExerciseTypeAndLessonId(EExerciseType eExerciseType, Long lessonId) {
        List<Exercise> exerciseList = iExerciseRepository.findExercisesByLessonAndType(lessonId, eExerciseType);
        return exerciseList.get(0);
    }

    private List<Exercise> getAllByLessonId(Long lessonId) {
        return iExerciseRepository.findAllByLessonId(lessonId);
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

    private void addFlashCardPopupPhrases(Ordered_objects object ,List<StructuredDataForExercisesDTO> exercisesToReturn, String type) {
        ObjectWithMediaDTO objectWithMediaDTO = phraseService.getPhraseWithMedia(object.getActivityId());
        exercisesToReturn.add(new StructuredDataForExercisesDTO(type, objectWithMediaDTO));
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
        Exercise exercise = getExerciseById(lessonId);
        ExercisesDetailsDTO exerciseDetails = new ExercisesDetailsDTO(exercise.getId(),exercise.getTopic(), exercise.getDescription());
        exercisesToReturn.add(new StructuredDataForExercisesDTO("details", exerciseDetails));
        List<Ordered_objects> orderedObjectsList = orderedObjectsService.getOrderedObjectsListByExerciseId(exercise.getId());
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
                case PHRASE -> addFlashCardPopupPhrases(index, exercisesToReturn, "flash_card_popup");
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
        int numCorrectAnswers = finalExerciseRequestDTO.getUserResponsesList().size() - incorrectIds.size();
        int percentage = cuntPercentage(numCorrectAnswers, incorrectIds.size());
        int exp = countExperience(numCorrectAnswers, percentage);
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

    private ExerciseDTO formExerciseDTO(Exercise exercise, boolean isFinished) {
        return new ExerciseDTO(exercise.getId(), exercise.getTopic(), isFinished);
    }


    private List<ExerciseListWithTypesDTO> getAllExercisesDTOByLessonIdAndCheckIsCompleted(Long lessonId, Long userId) {
        List<Exercise> exerciseList = getAllByLessonId(lessonId);
        List<UserProgress> userProgressList = userProgressService.getAllUserProgressExercisesBuETypeAndLessonIdAndUserId(EFinishedTypes.EXERCISES, lessonId, userId);
        List<ExerciseDTO> exerciseDTOListVideo = new ArrayList<>();
        List<ExerciseDTO> exerciseDTOListExercise = new ArrayList<>();
        List<ExerciseDTO> exerciseDTOListManga = new ArrayList<>();
        List<ExerciseListWithTypesDTO> typesDTOArrayList = new ArrayList<>();
        boolean isFinished = false;
        for (Exercise exercise : exerciseList) {
            for (UserProgress userProgress : userProgressList) {
                if(exercise.getId() == userProgress.getObjectFinishedId()) {
                    isFinished = true;
                } else {
                    isFinished = false;
                }
            }
            switch (exercise.getEExerciseType()) {
                case VIDEO -> exerciseDTOListVideo.add(formExerciseDTO(exercise, isFinished));
                case EXERCISE -> exerciseDTOListExercise.add(formExerciseDTO(exercise, isFinished));
                case MANGA -> exerciseDTOListManga.add(formExerciseDTO(exercise, isFinished));
                default -> new IllegalArgumentException("Exercise type don't exists");
            }
        }
        if(exerciseDTOListVideo.size() > 0) {
        typesDTOArrayList.add(new ExerciseListWithTypesDTO("VIDEO", exerciseDTOListVideo));
        }
        if(exerciseDTOListExercise.size() > 0) {
            typesDTOArrayList.add(new ExerciseListWithTypesDTO("EXERCISE", exerciseDTOListExercise));
        }
        if(exerciseDTOListManga.size() > 0) {
            typesDTOArrayList.add(new ExerciseListWithTypesDTO("MANGA", exerciseDTOListManga));
        }
        return typesDTOArrayList;
    }

    public List<ExerciseDTO> formNovelsListForCharacterByCharacterId(Long characterId, Long userId, List<Exercise> exerciseList) {
        List<ExerciseDTO> novelsList = new ArrayList<>();
        List<UserProgress> userProgressList = userProgressService.getAllUserProgressExercisesBuETypeAndLessonIdAndUserId(EFinishedTypes.NOVELS, characterId, userId);
        boolean isFinished = false;
        for (Exercise exercise : exerciseList) {
            for (UserProgress userProgress : userProgressList) {
                if (exercise.getId() == userProgress.getObjectFinishedId()) {
                    isFinished = true;
                } else {
                    isFinished = false;
                }
            }
            novelsList.add(formExerciseDTO(exercise, isFinished));
        }
        return novelsList;
    }

    public LessonDetailsDTO getLessonDetailsByLessonIdAndUserId(Long lessonId, Long userId) {
        List<Exercise> exerciseList = getAllByLessonId(lessonId);
        List<Ordered_objects> orderedObjectsList = new ArrayList<>();
        for (Exercise exercise : exerciseList) {
            orderedObjectsList.addAll(orderedObjectsService.getOrderedObjectsListByExerciseId(exercise.getId()));
        }
        List<Long> phraseIds = orderedObjectsList.stream()
                .filter(phrase -> phrase.getActivityType().equals(EActivityType.PHRASE))
                .map(phrase -> phrase.getActivityId())
                .collect(Collectors.toList());
        List<PhraseDTOWithSentence> phraseDTOWithSentenceList = new ArrayList<>();
        for (Long id : phraseIds) {
            phraseDTOWithSentenceList.add(phraseService.getPhraseByPhraseId(id));
        }
        Collections.shuffle(phraseDTOWithSentenceList);
        List<String> textList = new ArrayList<>();
        for (PhraseDTOWithSentence phraseDTOWithSentence : phraseDTOWithSentenceList) {
            int randomNumber = ThreadLocalRandom.current().nextInt(1, 4);
            if(randomNumber == 1) {
                textList.add(phraseDTOWithSentence.getTextDTO().getKanji_word());
            }
            if (randomNumber == 2 ) {
                textList.add(phraseDTOWithSentence.getTextDTO().getHiragana_or_katakana());
            }
            if (randomNumber == 3) {
                textList.add(phraseDTOWithSentence.getTextDTO().getRomanji_word());
            }
            if (randomNumber == 4) {
                textList.add(phraseDTOWithSentence.getTextDTO().getTranslation());
            }
        }
        List<ExplanationWithTableDTO> explanationList = new ArrayList<>();
        List<Long> exerciseIdsList = exerciseList.stream()
                .map(exercise -> exercise.getId())
                .collect(Collectors.toList());
        List<Long> guidanceIds = orderedObjectsService.getOrderedObjectsListByEAcivityTypeAndExerciseIdsList(EActivityType.STUDY_CONTENT, exerciseIdsList).stream()
                .map(orderObject -> orderObject.getActivityId())
                .collect(Collectors.toList());
        for (Long guidanceId : guidanceIds) {
            explanationList.add(guidanceService.getExplanationWithTable(guidanceId));
        }
        return new LessonDetailsDTO(getAllExercisesDTOByLessonIdAndCheckIsCompleted(lessonId, userId), textList, explanationList);
    }

    public VideoLessonDTO startVideoLesson(Long exerciseId) {
        Exercise exercise = getExerciseById(exerciseId);
        List<Ordered_objects> orderedObjectsList = orderedObjectsService.getOrderedObjectsListByExerciseId(exercise.getId());
        FileRecordsDTO fileRecordsDTO = fileRecordService.getByIdFileRecordsAndCreateDTO(orderedObjectsList.get(0).getActivityId());
        VideoLessonDTO videoLessonDTO = new VideoLessonDTO();
        videoLessonDTO.setExerciseId(exerciseId);
        videoLessonDTO.setFileRecords(fileRecordsDTO);
        videoLessonDTO.setSubtitleList(subtitleService.getSubtitleListOrderedByVideoId(fileRecordsDTO.getId()));
        return videoLessonDTO;
    }

    public List<StructuredDataForExercisesDTO> getMangaFromAllTables(Long exerciseId) {
        List<StructuredDataForExercisesDTO> mangaToReturn = new ArrayList<>();
        Exercise exercise = getExerciseById(exerciseId);
        List<Ordered_objects> orderedObjectsList = orderedObjectsService.getOrderedObjectsListByExerciseId(exercise.getId());
        orderedObjectsList.sort(Comparator.comparing(Ordered_objects::getOrderIndex));
        for (Ordered_objects index : orderedObjectsList) {
            switch (index.getActivityType()) {
                case QUESTION -> addQuestion(index, mangaToReturn);
                case QUESTION_COLOCATE -> addColocate(index, mangaToReturn);
                case QUESTION_ENGLISH_ANSWERS -> addQuestionInEnglishAnswers(index, mangaToReturn);
                case QUESTION_CHOOSE -> addQuestionChooseAnswers(index, mangaToReturn);
                case ERROR_CORRECTION -> addErrorCorrection(index, mangaToReturn);
                case PHRASE -> addFlashCardPopupPhrases(index, mangaToReturn, "centre_phrase");
                case RIGHT_PHRASE -> addFlashCardPopupPhrases(index, mangaToReturn, "right_phrase");
                case LEFT_PHRASE -> addFlashCardPopupPhrases(index, mangaToReturn, "left_phrase");
                case GUIDANCE -> addGuidance(index, mangaToReturn);
                default -> {
                }
            }
        }
        return mangaToReturn;
    }

        private void addGuidance(Ordered_objects object , List<StructuredDataForExercisesDTO> exercisesToReturn) {
            ObjectWithMediaDTO guidanceDTO = guidanceService.getExplanationGuidance(object.getActivityId());
            exercisesToReturn.add(new StructuredDataForExercisesDTO("guidance", guidanceDTO));
        }


        private void addNovelFinalLinesByCorrectAnswers(Ordered_objects index) {

        }


    private void addNovelPhrases(List<Ordered_objects> elements, List<StructuredDataForExercisesDTO> pracesAndQuestionList) {
        for (Ordered_objects element : elements) {
            pracesAndQuestionList.add(new StructuredDataForExercisesDTO("phrase", phraseService.getPhaseWithMediaAndWithoutByPhraseId(element.getActivityId())));
        }
    }

    private void addNovelQuestion(List<Ordered_objects> elements, List<StructuredDataForExercisesDTO> novelElements) {
        NovelQuestionDTO novelQuestionDTO = new NovelQuestionDTO();
        List<Object> phrasesFalseLine = new ArrayList<>();
        List<Object> phrasesTrueLine = new ArrayList<>();

        for (Ordered_objects element : elements) {
            switch (element.getActivityType()) {
                case QUESTION -> novelQuestionDTO.setQuestion(questionService.getQuestionWithAnswersWithoutMedia(element.getActivityId()));
                case PHRASE -> {
                    Object phrase = phraseService.getPhaseWithMediaAndWithoutByPhraseId(element.getActivityId());
                    if (isTrue(element)) {
                        phrasesTrueLine.add(phrase);
                    } else {
                        phrasesFalseLine.add(phrase);
                    }
                }
            }
        }

        novelQuestionDTO.setPhrasesFalseLine(phrasesFalseLine);
        novelQuestionDTO.setPhrasesTrueLine(phrasesTrueLine);
        novelElements.add(new StructuredDataForExercisesDTO("question", novelQuestionDTO));
    }

    private boolean isTrue(Ordered_objects element) {
        return (element.getOrderIndex() % 1000) / 10 == 1;
    }

    private void addNovelFinalCase(List<Ordered_objects> elements, List<NovelFinalScenarioDTO> finalDialogueList) {
        int needsCorrectAnswers = (elements.get(0).getOrderIndex() % 10000) / 100;
        List<Object> phraseList = new ArrayList<>();
        for (Ordered_objects element : elements) {
            if(element.getActivityType() == EActivityType.PHRASE) {
                phraseList.add(phraseService.getPhaseWithMediaAndWithoutByPhraseId(element.getActivityId()));
            }
        }
        finalDialogueList.add(new NovelFinalScenarioDTO(needsCorrectAnswers, phraseList));
    }

    public VisualNovelDTO formNovelByExerciseId(Long exerciseId) {
        List<Ordered_objects> orderedObjectsList = orderedObjectsService.getOrderedObjectsListByExerciseId(exerciseId);

        Map<Integer, List<Ordered_objects>> blocks = orderedObjectsList.stream()
                .collect(Collectors.groupingBy(object -> object.getOrderIndex() / 100));

        List<StructuredDataForExercisesDTO> novelElements = new ArrayList<>();
        List<NovelFinalScenarioDTO> finalDialogueList = new ArrayList<>();

        for (Map.Entry<Integer, List<Ordered_objects>> block : blocks.entrySet()) {
            List<Ordered_objects> elements = block.getValue();
            elements.sort(Comparator.comparing(Ordered_objects::getOrderIndex));
            if (!elements.isEmpty()) {
                if (elements.get(0).getOrderIndex() > 90000) {
                    addNovelFinalCase(elements, finalDialogueList);
                } else {
                    switch (elements.get(0).getActivityType()) {
                        case QUESTION -> addNovelQuestion(elements, novelElements);
                        case PHRASE -> addNovelPhrases(elements, novelElements);
                    }
                }
            }
        }

        return new VisualNovelDTO(novelElements, finalDialogueList);
    }
}
