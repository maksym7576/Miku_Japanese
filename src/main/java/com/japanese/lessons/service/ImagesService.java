package com.japanese.lessons.service;

import com.japanese.lessons.models.Images;
import com.japanese.lessons.repositories.IImagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImagesService {

    @Autowired
    IImagesRepository iImagesRepository;

    public void saveAllImages(List<Images> imagesList) {
        if(imagesList != null && !imagesList.isEmpty()) {
            iImagesRepository.saveAll(imagesList);
        } else {
            throw new IllegalArgumentException("Has occurred an error with saving imagesList");
        }
    }

    public void saveImage(Images images) {
        if(images != null) {
            iImagesRepository.save(images);
        } else {
            throw new IllegalArgumentException("Has occurred an error with saving image");
        }
    }
}
