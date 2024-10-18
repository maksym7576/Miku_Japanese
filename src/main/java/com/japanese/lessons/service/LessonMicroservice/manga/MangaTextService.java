package com.japanese.lessons.service.LessonMicroservice.manga;

import com.japanese.lessons.models.LessonMicroservice.manga.MangaText;
import com.japanese.lessons.repositories.LessonMisroservice.IMangaTextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MangaTextService {

    @Autowired
    IMangaTextRepository iMangaTextRepository;

    public void saveAllMangaText(List<MangaText> mangaTextList) {
        if (mangaTextList != null && !mangaTextList.isEmpty()){
             iMangaTextRepository.saveAll(mangaTextList);
        } else {
            throw new IllegalArgumentException("MangaText cannot have null");
        }
    }
    public void saveMangaTest(MangaText mangaText) {
        if (mangaText != null ) {
            iMangaTextRepository.save(mangaText);
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

    public void updateMangaText(Long id, MangaText mangaText) {
        if(iMangaTextRepository.existsById(id)) {
            mangaText.setId(id);
            iMangaTextRepository.save(mangaText);
        } else {
            throw new IllegalArgumentException("This text of manga isn't exists");
        }
    }
}
