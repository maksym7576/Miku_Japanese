package com.japanese.lessons.service;

import com.japanese.lessons.dtos.*;
import com.japanese.lessons.dtos.request.ExplanationWithPhrasesTableDTO;
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
    @Autowired private FileRecordService fileRecordService;
    private Exercise getExerciseByLessonId(Long lessonId) {
        return iExerciseRepository.findByLessonId(lessonId).orElseThrow(() -> new IllegalArgumentException("Exercise is not found"));
    }

    private void addQuestion(Ordered_objects object ,List<StructuredDataForExercisesDTO> exercisesToReturn) {
        Question question = questionService.getQuestionById(object.getObjectId());
        List<Text> answerList = mangaDialogueService.getAllTextByTypeAndObjectId(ETargetType.QUESTION_TABLE, question.getId());
        List<Image> image = new ArrayList<>();
        if(question.getEFileURLType() == EFileURLType.image) {
            image = imagesService.getImagesByObjectId(ETargetType.QUESTION_TABLE, question.getId());
        }
        FileRecords fileRecords = new FileRecords();
        if(question.getEFileURLType() == EFileURLType.video) {
            fileRecords = fileRecordService.getFileRecordsById(question.getObjectIdURL());
        }
        QuestionAnswerDTO questionAnswerDTO = new QuestionAnswerDTO(question, answerList, question.getEFileURLType() == EFileURLType.image ? image.get(0) : fileRecords );
        logger.debug("Get question: {}", question);
        exercisesToReturn.add(new StructuredDataForExercisesDTO("question", questionAnswerDTO));
    }

    private void addColocate(Ordered_objects object ,List<StructuredDataForExercisesDTO> exercisesToReturn) {
        Question question = questionService.getQuestionById(object.getObjectId());
        Audio audio = new Audio();
        Text text = mangaDialogueService.getTextByTypeAndObjectId(ETargetType.QUESTION_TABLE, object.getObjectId());
        if(question.getEFileURLType() == EFileURLType.audio) {
            audio = audioService.getByTypeAndObjectId(ETargetType.QUESTION_TABLE, object.getObjectId());
        }
        String[] arrayKanji = text.getKanji().split(",");
        String kanjiPhrase = String.join(" ", arrayKanji);
        String[] arrayHiragana_katakana = text.getHiragana_or_katakana().split(",");
        String hiragana_katakanaPhrase = String.join(" ", arrayHiragana_katakana);
        String[] arrayRomanji = text.getRomanji().split(",");
        String romanjiPhrase = String.join(" ", arrayRomanji);
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < arrayKanji.length; i++) {
            indices.add(i);
        }
        Collections.shuffle(indices);
        String[] shuffledKanji = new String[arrayKanji.length];
        String[] shuffledHiraganaKatakana = new String[arrayHiragana_katakana.length];
        String[] shuffledRomanji = new String[arrayRomanji.length];

        for (int i = 0; i < indices.size(); i++) {
            int shuffledIndex = indices.get(i);
            shuffledKanji[i] = arrayKanji[shuffledIndex];
            shuffledHiraganaKatakana[i] = arrayHiragana_katakana[shuffledIndex];
            shuffledRomanji[i] = arrayRomanji[shuffledIndex];
        }
        ColocateWordsDTO colocateWordsDTO = new ColocateWordsDTO(
                text.getId(), kanjiPhrase, hiragana_katakanaPhrase, romanjiPhrase, text.getTranslation(),
                shuffledKanji, shuffledHiraganaKatakana, shuffledRomanji
        );
        ColocateExerciseDTO colocateExerciseDTO = new ColocateExerciseDTO(question, colocateWordsDTO, audio);
        exercisesToReturn.add(new StructuredDataForExercisesDTO("exercise_colocate", colocateExerciseDTO));
    }

    private void addFact(Ordered_objects object ,List<StructuredDataForExercisesDTO> exercisesToReturn) {

    }

    private void addStudyContentNewWords(Ordered_objects object , List<StructuredDataForExercisesDTO> exercisesToReturn) {
        ExplanationWithTableDTO explanationWithTableDTO = guidanceService.getExplanationWithTables(object.getObjectId());
        exercisesToReturn.add(new StructuredDataForExercisesDTO("explanation_with_table", explanationWithTableDTO));
    }

    private void addStudyContentPhrases(Ordered_objects object , List<StructuredDataForExercisesDTO> exercisesToReturn) {
        ExplanationWithPhrasesTableDTO explanationWithPhrasesTableDTO = guidanceService.getExplanationWithPhrasesTable(object.getObjectId());
        exercisesToReturn.add(new StructuredDataForExercisesDTO("explanation_with_phrases", explanationWithPhrasesTableDTO));
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
    private void addFlashCardPopupPhrases(Ordered_objects object ,List<StructuredDataForExercisesDTO> exercisesToReturn) {
        Text text = mangaDialogueService.getMangaDialogueById(object.getObjectId());
        FileRecords fileRecords = new FileRecords();
        if(text.getTargetType() == ETargetType.FILE_RECORDS_TABLE) {
            fileRecords = fileRecordService.getFileRecordsById(text.getTargetId());
        }
        ObjectWithVideoDTO objectWithVideoDTO = new ObjectWithVideoDTO(text ,fileRecords);
        exercisesToReturn.add(new StructuredDataForExercisesDTO("flash_card_popup_phrases", objectWithVideoDTO));
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
                case STUDY_CONTENT_PHRASES -> addStudyContentPhrases(index, exercisesToReturn);
                case FLASHCARD_POPUP_PHRASES -> addFlashCardPopupPhrases(index, exercisesToReturn);
                default -> {}
            }
        }
        return exercisesToReturn;
    }
}
