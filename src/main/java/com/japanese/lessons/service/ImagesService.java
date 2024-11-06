package com.japanese.lessons.service;

import com.japanese.lessons.models.ETargetType;
import com.japanese.lessons.models.lesson.mangaExercise.Image;
import com.japanese.lessons.models.lesson.mangaExercise.Manga;
import com.japanese.lessons.repositories.IImagesRepository;
import com.japanese.lessons.service.Lesson.MangaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ImagesService {

    @Autowired
    IImagesRepository iImagesRepository;

    public List<Image> getImagesByMangaId(Long mangaId) {
        if (mangaId == null) {
            throw new IllegalArgumentException("Manga ID cannot be null.");
        }

        List<Image> images = iImagesRepository.findByTargetTypeAndTargetId(ETargetType.MANGA, mangaId);

        if (images == null || images.isEmpty()) {
            throw new IllegalArgumentException("No images found for manga ID: " + mangaId);
        }

        return images;
    }

    @Transactional
    public Image saveImage(Image image) {
        return iImagesRepository.save(image);
    }

}
