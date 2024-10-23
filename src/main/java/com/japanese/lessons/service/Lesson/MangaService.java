package com.japanese.lessons.service.Lesson;

import com.japanese.lessons.models.lesson.Manga;
import com.japanese.lessons.repositories.Lesson.IMangaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MangaService {

    @Autowired
    IMangaRepository iMangaRepository;

    @Autowired
    LessonService lessonService;

    public Manga getMangaById(Long id) {
        return iMangaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Manga not found with id: " + id));
    }

    public Manga saveManga(Manga manga) {
        if (manga == null) {
            throw new IllegalArgumentException("Manga cannot be null.");
        } else {
            manga.validateCompletion();
            lessonService.getLessonById(manga.getLesson().getId());
                return iMangaRepository.save(manga);
        }
    }

    @Transactional
    public void updateManga(Long id, Manga updatedManga) {
        Manga existingManga = getMangaById(id);
        existingManga.copyNonNullProperties(updatedManga);
        saveManga(existingManga);
    }

    public void deleteManga(Long id) {
        if (iMangaRepository.existsById(id)) {
            iMangaRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Manga doesn't exist.");
        }
    }
}
