package com.japanese.lessons.service.LessonMicroservice.manga;

import com.japanese.lessons.models.LessonMicroservice.manga.Manga_dialogs;
import com.japanese.lessons.repositories.LessonMisroservice.IMangaTextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MangaTextService {

    @Autowired
    IMangaTextRepository iMangaTextRepository;

    public void saveAllMangaText(List<Manga_dialogs> mangadialogsList) {
        if (mangadialogsList != null && !mangadialogsList.isEmpty()){
             iMangaTextRepository.saveAll(mangadialogsList);
        } else {
            throw new IllegalArgumentException("MangaText cannot have null");
        }
    }
    public void saveMangaTest(Manga_dialogs mangadialogs) {
        if (mangadialogs != null ) {
            iMangaTextRepository.save(mangadialogs);
        } else {
            throw new IllegalArgumentException("MangaText cannot have empty test.");
        }
    }
    public void deleteMangaTest(Long id) {
        if(iMangaTextRepository.existsById(id)) {
            iMangaTextRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("This text of manga isn't exists");
        }
    }

    public void updateMangaText(Long id, Manga_dialogs mangadialogs) {
        if(iMangaTextRepository.existsById(id)) {
            mangadialogs.setId(id);
            iMangaTextRepository.save(mangadialogs);
        } else {
            throw new IllegalArgumentException("This text of manga isn't exists");
        }
    }
}
