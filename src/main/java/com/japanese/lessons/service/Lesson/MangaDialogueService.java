package com.japanese.lessons.service.Lesson;

import com.japanese.lessons.models.lesson.mangaExercise.MangaDialogue;
import com.japanese.lessons.repositories.Lesson.IMangaTextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MangaDialogueService {

    @Autowired
    IMangaTextRepository iMangaTextRepository;

    public MangaDialogue getMangaDialogueById(Long id) {
        return iMangaTextRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("MangaDialogue not found with id: " + id));
    }

    public void saveAllMangaDialogues(List<MangaDialogue> mangaDialogueList) {
        if (mangaDialogueList != null && !mangaDialogueList.isEmpty()) {
            iMangaTextRepository.saveAll(mangaDialogueList);
        } else {
            throw new IllegalArgumentException("MangaDialogue list cannot be null or empty.");
        }
    }

    public void saveMangaDialogue(MangaDialogue mangaDialogue) {
        validMangaDialogueIsNull(mangaDialogue);
        if (mangaDialogue.isComplete()) {
            iMangaTextRepository.save(mangaDialogue);
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

    private void validMangaDialogueIsNull(MangaDialogue mangaDialogue) {
        if (mangaDialogue == null) {
            throw new IllegalArgumentException("MangaDialogue is null");
        }
    }

    public void updateMangaDialogue(Long id, MangaDialogue updatedMangaDialogue) {
        MangaDialogue existingMangaDialogue = getMangaDialogueById(id);
        existingMangaDialogue.copyNonNullProperties(updatedMangaDialogue);
        saveMangaDialogue(existingMangaDialogue);
    }
}
