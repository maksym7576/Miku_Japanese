package com.japanese.lessons.service.LessonMicroservice.manga;

import com.japanese.lessons.models.Images;
import com.japanese.lessons.models.LessonMicroservice.manga.Manga;
import com.japanese.lessons.models.LessonMicroservice.manga.MangaText;
import com.japanese.lessons.models.LessonMicroservice.test.Answer;
import com.japanese.lessons.models.LessonMicroservice.test.Question;
import com.japanese.lessons.repositories.LessonMisroservice.IMangaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MangaService {

    @Autowired
    IMangaRepository iMangaRepository;

    public Manga getMangaById(Long id) {
        return iMangaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Has occurred an error with manga"));
    }
//    public Manga sortFiles() {
//
//    }

    public Manga createManga(Manga manga, List<MangaText> mangaTexts, List<Question> questions, List<Answer> answers, List<Images> images) {
        return
    }
}
