package com.japanese.lessons.service.LessonMicroservice.manga;

import com.japanese.lessons.models.LessonMicroservice.manga.Manga;
import com.japanese.lessons.repositories.LessonMisroservice.IMangaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
