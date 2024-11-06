package com.japanese.lessons.service.Lesson;

import com.japanese.lessons.models.ETargetType;
import com.japanese.lessons.models.lesson.mangaExercise.MangaPhotoDescription;
import com.japanese.lessons.repositories.Lesson.IMangaPhotoDescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MangaPhotoDescriptionService {

    @Autowired
    IMangaPhotoDescriptionRepository iMangaPhotoDescriptionRepository;

    public List<MangaPhotoDescription> getMangaPhotoDescriptionByImageId(Long imageId) {
        if (imageId == null) {
            throw new IllegalArgumentException("Image ID cannot be null.");
        }

        List<MangaPhotoDescription> descriptions = iMangaPhotoDescriptionRepository.findByTargetTypeAndTargetId(ETargetType.IMAGE, imageId);

        if (descriptions == null || descriptions.isEmpty()) {
            throw new IllegalArgumentException("No descriptions found for image ID: " + imageId);
        }

        return descriptions;
    }

}
