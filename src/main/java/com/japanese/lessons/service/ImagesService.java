package com.japanese.lessons.service;

import com.japanese.lessons.models.ETargetType;
import com.japanese.lessons.models.lesson.mangaExercise.Image;
import com.japanese.lessons.repositories.IImagesRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ImagesService {

    @Autowired
    IImagesRepository iImagesRepository;

    public List<Image> getImagesByObjectId(ETargetType eTargetType, Long objectId) {
        List<Image> response = iImagesRepository.findByTargetTypeAndTargetId(eTargetType, objectId);
        return response != null ? response : Collections.emptyList();
    }

    @Transactional
    public Image saveImage(Image image) {
        return iImagesRepository.save(image);
    }

}
