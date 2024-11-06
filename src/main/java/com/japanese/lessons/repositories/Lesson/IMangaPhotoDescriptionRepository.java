package com.japanese.lessons.repositories.Lesson;

import com.japanese.lessons.models.ETargetType;
import com.japanese.lessons.models.lesson.mangaExercise.MangaPhotoDescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IMangaPhotoDescriptionRepository extends JpaRepository<MangaPhotoDescription, Long> {
    List<MangaPhotoDescription> findByTargetTypeAndTargetId(ETargetType targetType, Long targetId);
}
