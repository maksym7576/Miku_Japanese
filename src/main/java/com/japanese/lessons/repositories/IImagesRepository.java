package com.japanese.lessons.repositories;

import com.japanese.lessons.models.lesson.mangaExercise.Images;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IImagesRepository extends JpaRepository<Images, Long> {
    List<Images> findByMangaId(Long imageId);
}
