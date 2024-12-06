package com.japanese.lessons.service.Lesson;

import com.japanese.lessons.dtos.response.models.TextDTO;
import com.japanese.lessons.models.sixsth.Text;
import com.japanese.lessons.repositories.Lesson.IMangaTextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MangaDialogueService {

    @Autowired private IMangaTextRepository iMangaTextRepository;

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

//    public List<Text> getAllTextByTypeAndObjectId(ETargetType eTargetType, Long objectId) {
////        List<Text> response = iMangaTextRepository.findAllByTargetTypeAndTargetId(eTargetType, objectId);
////        return response != null ? response : Collections.emptyList();
//    }
//    public Text getTextByTypeAndObjectId(ETargetType eTargetType, Long objectId) {
////        return iMangaTextRepository.findByTargetTypeAndTargetId(eTargetType, objectId);
//    }

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

    public List<TextDTO>  getTextDTOListByIdsList(List<Long> idsList) {
        List<Text> textList = getTextByIdsList(idsList);
        for (Text text : textList) {
            uniteHiraganaKanji(text);
        }
        return formTextDTOList(textList);
    }

    private Text uniteHiraganaKanji (Text text) {
        text.setKanji(text.getKanji().replaceAll("\\s+", ""));
        text.setHiragana_or_katakana(text.getHiragana_or_katakana().replaceAll("\\s+", ""));
        return text;
    }

    public TextDTO createTextDTOByTargetId(Long targetId) {
        Text text = getMangaDialogueById(targetId);
        return formTextDTO(text);
    }
}
