package com.japanese.lessons.service.Lesson;

import com.japanese.lessons.models.ETargetType;
import com.japanese.lessons.models.lesson.mangaExercise.Text;
import com.japanese.lessons.repositories.Lesson.IMangaTextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class MangaDialogueService {

    @Autowired
    IMangaTextRepository iMangaTextRepository;

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

    public List<Text> getAllTextByTypeAndObjectId(ETargetType eTargetType, Long objectId) {
        List<Text> response = iMangaTextRepository.findAllByTargetTypeAndTargetId(eTargetType, objectId);
        return response != null ? response : Collections.emptyList();
    }
    public Text getTextByTypeAndObjectId(ETargetType eTargetType, Long objectId) {
        return iMangaTextRepository.findByTargetTypeAndTargetId(eTargetType, objectId);
    }

    public List<Text> getTextByIdsList(List<Long> ids) {
        return iMangaTextRepository.findAllById(ids);
    }
}
