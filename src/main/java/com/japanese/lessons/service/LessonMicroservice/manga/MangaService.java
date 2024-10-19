package com.japanese.lessons.service.LessonMicroservice.manga;

import com.japanese.lessons.models.LessonMicroservice.manga.Manga;
import com.japanese.lessons.repositories.LessonMisroservice.IMangaRepository;
import com.japanese.lessons.service.LessonMicroservice.test.QuestionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MangaService {

    @Autowired
    IMangaRepository iMangaRepository;

    public Manga getMangaById(Long id) {
        return iMangaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Manga not found with id: " + id));
    }

    public Manga saveManga(Manga manga) {
        if (manga == null) {
            throw new IllegalArgumentException("Manga cannot be null.");
        }
        return iMangaRepository.save(manga);
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
