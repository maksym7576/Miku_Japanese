package com.japanese.lessons.service;

import com.japanese.lessons.models.Images;
import com.japanese.lessons.models.LessonMicroservice.manga.Manga;
import com.japanese.lessons.repositories.IImagesRepository;
import com.japanese.lessons.service.LessonMicroservice.manga.MangaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Service
public class ImagesService {

    @Autowired
    IImagesRepository iImagesRepository;

    @Autowired
    MangaService mangaService;

    public List<Images> getImagesByFlightId(Long manga_id) {
        return iImagesRepository.findByMangaId(manga_id);
    }

    public void uploadImage(Long mangaId, int turn, MultipartFile file) throws Exception {
        Manga manga = mangaService.getMangaById(mangaId);
        if (manga == null) {
            throw new Exception("Manga not found.");
        }

        byte[] imageBytes = file.getBytes();
        Images images = new Images();
        images.setImageData(imageBytes);
        images.setManga(manga);
        images.setTurn(turn);

        saveImage(images);
    }

    @Transactional
    public Images saveImage(Images images) {
        return iImagesRepository.save(images);
    }

    public void uploadAllImages()
}
