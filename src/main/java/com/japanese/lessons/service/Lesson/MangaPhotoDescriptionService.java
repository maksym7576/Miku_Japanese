package com.japanese.lessons.service.Lesson;

import com.japanese.lessons.models.ETargetType;
import com.japanese.lessons.models.lesson.mangaExercise.MangaPhotoDescription;
import com.japanese.lessons.repositories.Lesson.IMangaPhotoDescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MangaPhotoDescriptionService {

    @Autowired
    IMangaPhotoDescriptionRepository iMangaPhotoDescriptionRepository;

    public List<MangaPhotoDescription> getMangaPhotoDescriptionByImageId(Long imageId) {
        List<MangaPhotoDescription> response = iMangaPhotoDescriptionRepository.findByTargetTypeAndTargetId(ETargetType.IMAGE, imageId);
        return response != null ? response : Collections.emptyList();
    }

}
