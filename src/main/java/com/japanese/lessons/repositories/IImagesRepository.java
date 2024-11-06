package com.japanese.lessons.repositories;

import com.japanese.lessons.models.ETargetType;
import com.japanese.lessons.models.lesson.mangaExercise.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IImagesRepository extends JpaRepository<Image, Long> {
    List<Image> findByTargetTypeAndTargetId(ETargetType targetType, Long targetId);
}

