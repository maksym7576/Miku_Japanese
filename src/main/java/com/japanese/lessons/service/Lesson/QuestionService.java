package com.japanese.lessons.service.Lesson;

import com.japanese.lessons.dtos.*;
import com.japanese.lessons.dtos.response.question.ColocateExerciseDTO;
import com.japanese.lessons.dtos.response.question.ColocateWordsDTO;
import com.japanese.lessons.dtos.response.models.QuestionDTO;
import com.japanese.lessons.dtos.response.models.TextDTO;
import com.japanese.lessons.models.fourth.Question;
import com.japanese.lessons.models.sixsth.Text;
import com.japanese.lessons.repositories.Lesson.IQuestionRepository;
import com.japanese.lessons.service.ExerciseService;
import com.japanese.lessons.service.FileRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    private static final Logger logger = LoggerFactory.getLogger(ExerciseService.class);
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
        return new QuestionDTO(question.getId(), question.getQuestion(), question.getDescription());
    }

    private QuestionWithAnswerDTO getQuestionWithAnswers(Question question) {
        List<QuestionJsonStandardDTO> idsList = question.getStandardFormat();
        List<Long> ids = new ArrayList<>( idsList.stream()
                .map(QuestionJsonStandardDTO::getTextId)
                .collect(Collectors.toList()));
        List<TextDTO> textDTOList = mangaDialogueService.getTextDTOListByIdsListWithUnitingWords(ids);
        List<AnswerDTO> answerDTOList = new ArrayList<>();
        for (TextDTO textDTO : textDTOList) {
            for (QuestionJsonStandardDTO questionJsonStandardDTO : idsList) {
                if (questionJsonStandardDTO.getTextId() == textDTO.getId()) {

                    answerDTOList.add(new AnswerDTO(textDTO.getId(),
                            textDTO.getKanji_word(), textDTO.getHiragana_or_katakana(), textDTO.getRomanji_word(),
                            textDTO.getTranslation(), questionJsonStandardDTO.isCorrect()));
                }
            }
        }
        return new QuestionWithAnswerDTO(formQuestion(question), answerDTOList);
    }

    private ColocateExerciseDTO getColocateWithWords(Question question) {
        List<QuestionJsonStandardDTO> idsList = question.getStandardFormat();
        List<Long> ids = new ArrayList<>(idsList.stream()
                .map(QuestionJsonStandardDTO::getTextId)
                .collect(Collectors.toList()));
        List<TextDTO> textDTOList = mangaDialogueService.getTextDTOListByIdsList(ids);
        TextDTO textDTO = textDTOList.get(0);
       return new ColocateExerciseDTO(formQuestion(question), mangaDialogueService.splitPhrase(textDTO));
    }

    public ColocateWordsWithCorrectWordsListDTO addErrorCorrection (Long questionId) {
        Question question = getQuestionById(questionId);
        List<QuestionJsonIndexDTO> questionIncorrectWordsList = question.getIndexFormat();
        List<Long> idsList = questionIncorrectWordsList.stream()
                .map(word -> word.getTextId())
                .collect(Collectors.toList());
        logger.debug("IdsList ErrorCorrection {}", idsList);
        List<TextDTO> textDTOList = mangaDialogueService.getTextDTOListByIdsList(idsList);
        TextDTO dialogueTextDTO = new TextDTO();
        for (QuestionJsonIndexDTO questionJsonIndexDTO : questionIncorrectWordsList) {
            if(questionJsonIndexDTO.getIndex() == 100) {
                for (TextDTO textDTO : textDTOList) {
                    if(textDTO.getId().equals(questionJsonIndexDTO.getTextId())) {
                        dialogueTextDTO = textDTO;
                    }
                }
            }
        }
        logger.debug("Dialogue id {}", dialogueTextDTO.getId());
        String[] arrayKanji = dialogueTextDTO.getKanji_word().split("/");
        String phraseKanji = String.join("", arrayKanji);
        String[] arrayHiragana = dialogueTextDTO.getHiragana_or_katakana().split("/");
        String phraseHiragana = String.join("", arrayHiragana);
        String[] arrayRomanji = dialogueTextDTO.getRomanji_word().split("/");
        String phraseRomanji = String.join(" ", arrayKanji);
        String[] correctWordsKanji = new String[textDTOList.size() - 1];
        logger.debug("CorrectWordsKanji length {}", correctWordsKanji.length);
        String[] correctsWordsHiraganaKatakana = new String[textDTOList.size() -1];
        String[] correctWordsRomanji = new String[textDTOList.size() -1];
        for (QuestionJsonIndexDTO questionJsonIndexDTO : questionIncorrectWordsList) {
            if(questionJsonIndexDTO.getIndex() >= 1 && questionJsonIndexDTO.getIndex() !=100) {
                for (TextDTO textDTO : textDTOList) {
                    if(textDTO.getId().equals(questionJsonIndexDTO.getTextId())) {
                        for (int i = 0; i < correctWordsKanji.length; i++) {
                            if (correctWordsKanji[i] == null) {
                                correctWordsKanji[i] = arrayKanji[questionJsonIndexDTO.getIndex()];
                                correctsWordsHiraganaKatakana[i] = arrayHiragana[questionJsonIndexDTO.getIndex()];
                                correctWordsRomanji[i] = arrayRomanji[questionJsonIndexDTO.getIndex()];
                                break;
                            }
                        }
                        arrayKanji[questionJsonIndexDTO.getIndex() -1] = textDTO.getKanji_word();
                        arrayHiragana[questionJsonIndexDTO.getIndex() -1] = textDTO.getHiragana_or_katakana();
                        arrayRomanji[questionJsonIndexDTO.getIndex() -1] = textDTO.getRomanji_word();
                    }
                }
            }
        }
        for (int i =0; i < correctWordsKanji.length; i++) {
            if(correctWordsKanji[i] == null) {
                for (TextDTO textDTO :textDTOList) {
                    for (QuestionJsonIndexDTO questionJsonIndexDTO : questionIncorrectWordsList) {
                        if(textDTO.getId() == questionJsonIndexDTO.getTextId() && questionJsonIndexDTO.getIndex() == 0) {
                            correctWordsKanji[i] = textDTO.getKanji_word();
                            correctsWordsHiraganaKatakana[i] = textDTO.getHiragana_or_katakana();
                            correctWordsRomanji[i] = textDTO.getRomanji_word();
                        }
                    }
                }
            }
        }
        ColocateWordsDTO colocateWordsDTO = new ColocateWordsDTO(dialogueTextDTO.getId(), phraseKanji, phraseHiragana, phraseRomanji,
                dialogueTextDTO.getTranslation(), arrayKanji, arrayHiragana, arrayRomanji);
        ColocateWordsWithCorrectWordsListDTO correctWordsListDTO = new ColocateWordsWithCorrectWordsListDTO(colocateWordsDTO, correctWordsKanji, correctsWordsHiraganaKatakana, correctWordsRomanji);
        return correctWordsListDTO;
    }

    private QuestionWithEnglishAnswerDTO getQuestionWithEnglishAnswer(Question question) {
        QuestionDTO questionDTO = formQuestion(question);
        List<QuestionJsonEnglishTextDTO> questionJsonEnglishTextDTOS = question.getEnglishFormat();
        Collections.shuffle(questionJsonEnglishTextDTOS);
        return new QuestionWithEnglishAnswerDTO(questionDTO, questionJsonEnglishTextDTOS);
    }

    public QuestionChooseDTO getQuestionChoose(Long questionId) {
        Question question = getQuestionById(questionId);
        List<QuestionJsonChooseTheContinuationDTO> jsonList = question.getChooseTheContinuationFormat();
        List<Long> idsText = jsonList.stream()
                .map(text -> text.getTextId())
                .collect(Collectors.toList());
        List<TextDTO> textDTOList = mangaDialogueService.getTextDTOListByIdsList(idsText);
        Collections.shuffle(jsonList);
        Collections.shuffle(textDTOList);
        return new QuestionChooseDTO(formQuestion(question), jsonList, textDTOList);
    }

    public ObjectWithMediaDTO addMediaToQuestionAndGetQuestion(Long questionId, String questionType) {
        Question question = getQuestionById(questionId);
        ObjectWithMediaDTO objectWithMediaDTO = new ObjectWithMediaDTO();
        switch (questionType) {
            case "QUESTION" ->  objectWithMediaDTO.setObject(getQuestionWithAnswers(question));
            case "QUESTION_ENGLISH" -> objectWithMediaDTO.setObject(getQuestionWithEnglishAnswer(question));
            case "COLOCATE" -> objectWithMediaDTO.setObject(getColocateWithWords(question));
            default -> {}
        };
        MediaPackageDTO mediaPackageDTO = new MediaPackageDTO(question.getEMediaType().toString(),(fileRecordService.createListFileRecordsDTOByIdsList(question.getIdsMedia())));
        objectWithMediaDTO.setMediaPackage(mediaPackageDTO);;
        return objectWithMediaDTO;
    }
}
