package com.japanese.lessons.service.Lesson;

import com.japanese.lessons.dtos.response.models.TextDTO;
import com.japanese.lessons.dtos.response.question.ColocateWordsDTO;
import com.japanese.lessons.models.sixsth.Text;
import com.japanese.lessons.repositories.Lesson.IMangaTextRepository;
import com.japanese.lessons.service.ExerciseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MangaDialogueService {

    @Autowired private IMangaTextRepository iMangaTextRepository;
    private static final Logger logger = LoggerFactory.getLogger(ExerciseService.class);

    public Text getMangaDialogueById(Long id) {
        return iMangaTextRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("MangaDialogue not found with id: " + id));
    }

    public void saveAllMangaDialogues(List<Text> textList) {
        if (textList != null && !textList.isEmpty()) {
            iMangaTextRepository.saveAll(textList);
        } else {
            throw new IllegalArgumentException("MangaDialogue list cannot be null or empty.");
        }
    }

    public void saveMangaDialogue(Text text) {
        validMangaDialogueIsNull(text);
        if (text.isComplete()) {
            iMangaTextRepository.save(text);
        } else {
            throw new IllegalArgumentException("MangaDialogue is not complete.");
        }
    }

    public void deleteMangaDialogue(Long id) {
        if (iMangaTextRepository.existsById(id)) {
            iMangaTextRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("MangaDialogue doesn't exist.");
        }
    }

    private void validMangaDialogueIsNull(Text text) {
        if (text == null) {
            throw new IllegalArgumentException("MangaDialogue is null");
        }
    }

    public void updateMangaDialogue(Long id, Text updatedText) {
        Text existingText = getMangaDialogueById(id);
        existingText.copyNonNullProperties(updatedText);
        saveMangaDialogue(existingText);
    }

    public List<Text> getTextByIdsList(List<Long> ids) {
        return iMangaTextRepository.findAllById(ids);
    }

    private TextDTO formTextDTO(Text text) {
        return new TextDTO(text.getId(),
                text.getKanji(),text.getHiragana_or_katakana(),
                text.getRomanji(), text.getTranslation());
    }

    private List<TextDTO> formTextDTOList(List<Text> textList) {
        List<TextDTO> textDTOList = new ArrayList<>();
        for (Text text : textList) {
            textDTOList.add(formTextDTO(text));
        }
        return textDTOList;
    }

    public List<TextDTO> getTextDTOListByIdsListWithUnitingWords(List<Long> idsList) {
        logger.debug("ids list in getTextDTOListByIdsListWithUnitingWords {}", idsList);
        List<Text> textList = getTextByIdsList(idsList);
        Map<Long, Integer> idsIndexMap = new HashMap<>();
        for (int i = 0; i < idsList.size(); i++) {
            idsIndexMap.put(idsList.get(i), i);
        }
        textList.sort(Comparator.comparingInt(text -> idsIndexMap.get(text.getId())));
        for (Text text : textList) {
            logger.debug("Text id : {}", text.getId());
        }
        List<TextDTO> textDTOList = formTextDTOList(textList);
        for (TextDTO textDTO : textDTOList) {
            uniteHiraganaKanji(textDTO);
        }
        return textDTOList;
    }

    public TextDTO getTextDTOByIdWithUnitingWords(Long textId) {
        Text text = getMangaDialogueById(textId);
        TextDTO textDTO = formTextDTO(text);
            uniteHiraganaKanji(textDTO);
        return textDTO;
    }

    public List<TextDTO>  getTextDTOListByIdsList(List<Long> idsList) {
        List<Text> textList = getTextByIdsList(idsList);
        return formTextDTOList(textList);
    }

    public ColocateWordsDTO splitPhrase(TextDTO textDTO) {
        String[] arrayKanji = textDTO.getKanji_word().split("/");
        String kanjiPhrase = String.join("", arrayKanji);
        String[] arrayHiragana_katakana = textDTO.getHiragana_or_katakana().split("/");
        String hiragana_katakanaPhrase = String.join("", arrayHiragana_katakana);
        String[] arrayRomanji = textDTO.getRomanji_word().split("/");
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
        return new ColocateWordsDTO(
                textDTO.getId(), kanjiPhrase, hiragana_katakanaPhrase, romanjiPhrase, textDTO.getTranslation(),
                shuffledKanji, shuffledHiraganaKatakana, shuffledRomanji
        );
    }

    public TextDTO uniteHiraganaKanji (TextDTO textDTO) {
        textDTO.setKanji_word(textDTO.getKanji_word().replaceAll("/", ""));
        textDTO.setHiragana_or_katakana(textDTO.getHiragana_or_katakana().replaceAll("/", ""));
        textDTO.setRomanji_word(textDTO.getRomanji_word().replaceAll("/", " "));
        return textDTO;
    }

    public TextDTO createTextDTOByTargetId(Long targetId) {
        Text text = getMangaDialogueById(targetId);
        return formTextDTO(text);
    }
}
