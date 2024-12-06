package com.japanese.lessons.service.Lesson;

import com.japanese.lessons.dtos.AnswerDTO;
import com.japanese.lessons.dtos.ObjectWithMediaDTO;
import com.japanese.lessons.dtos.QuestionWithAnswerDTO;
import com.japanese.lessons.dtos.response.question.ColocateExerciseDTO;
import com.japanese.lessons.dtos.response.question.ColocateWordsDTO;
import com.japanese.lessons.dtos.response.models.QuestionDTO;
import com.japanese.lessons.dtos.response.models.TextDTO;
import com.japanese.lessons.models.fourth.Question;
import com.japanese.lessons.repositories.Lesson.IQuestionRepository;
import com.japanese.lessons.service.FileRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired private IQuestionRepository iQuestionRepository;
    @Autowired private MangaDialogueService mangaDialogueService;
    @Autowired private FileRecordService fileRecordService;

    public void saveQuestion(Question question) {
        iQuestionRepository.save(question);
    }

    public void deleteQuestion(Long id) {
        if(iQuestionRepository.existsById(id)) {
            iQuestionRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Question isn't exists");
        }
    }
    public void updateQuestion(Long id, Question updatedQuestion) {
      Question responceQuestion = getQuestionById(id);
      iQuestionRepository.save(responceQuestion);
    }
    private Question getQuestionById(Long id) {
        return iQuestionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("This question isn't exists"));
    }

    private QuestionDTO formQuestion(Question question) {
        Map<Long, Boolean> answersMap = question.getIdsAnswers();
        List<AnswerDTO> answerDTOList = answersMap.entrySet()
                .stream()
                .map(entry -> new AnswerDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
        return new QuestionDTO(question.getId(), question.getQuestion(), question.getDescription(), answerDTOList);
    }

    private QuestionWithAnswerDTO getQuestionWithAnswers(Question question) {
        Map<Long, Boolean> answersMap = question.getIdsAnswers();
        List<Long> ids = new ArrayList<>(answersMap.keySet());
        List<TextDTO> answerDTOList = mangaDialogueService.getTextDTOListByIdsList(ids);
        return new QuestionWithAnswerDTO(formQuestion(question), answerDTOList);
    }

    private ColocateExerciseDTO getColocateWithWords(Question question) {
        Map<Long, Boolean> answersMap = question.getIdsAnswers();
        List<Long> ids = new ArrayList<>(answersMap.keySet());
        List<TextDTO> textDTOList = mangaDialogueService.getTextDTOListByIdsList(ids);
        TextDTO text = textDTOList.get(0);
        String[] arrayKanji = text.getKanji_word().split(" ");
        String kanjiPhrase = String.join("", arrayKanji);
        String[] arrayHiragana_katakana = text.getHiragana_or_katakana().split(" ");
        String hiragana_katakanaPhrase = String.join("", arrayHiragana_katakana);
        String[] arrayRomanji = text.getRomanji_word().split(" ");
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
       return new ColocateExerciseDTO(formQuestion(question), colocateWordsDTO);
    }

    public ObjectWithMediaDTO addMediaToQuestion(Long questionId, String questionType) {
        Question question = getQuestionById(questionId);
        ObjectWithMediaDTO objectWithMediaDTO = new ObjectWithMediaDTO();
        switch (questionType) {
            case "QUESTION" ->  objectWithMediaDTO.setObject(getQuestionWithAnswers(question));
            case "COLOCATE" -> objectWithMediaDTO.setObject(getColocateWithWords(question));
            default -> {}
        };
        objectWithMediaDTO.setFileRecordsList(fileRecordService.createListFileRecordsDTOByIdsList(question.getIdsMedia()));
        return objectWithMediaDTO;
    }
}
