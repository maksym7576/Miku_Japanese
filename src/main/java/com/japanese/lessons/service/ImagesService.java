package com.japanese.lessons.service;

import com.japanese.lessons.models.ETargetType;
import com.japanese.lessons.models.lesson.mangaExercise.Image;
import com.japanese.lessons.repositories.IImagesRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImagesService {

    @Autowired
    IImagesRepository iImagesRepository;

    public List<Image> getImagesByObjectId(ETargetType eTargetType, Long objectId) {
        if (objectId == null) {
            throw new IllegalArgumentException("Object ID cannot be null.");
        }

        List<Image> images = iImagesRepository.findByTargetTypeAndTargetId(eTargetType, objectId);

        if (images == null || images.isEmpty()) {
            throw new IllegalArgumentException("No images found for object ID: " + objectId);
        }

        return images;
    }

    @Transactional
    public Image saveImage(Image image) {
        return iImagesRepository.save(image);
    }

}
