package com.japanese.lessons.repositories.Lesson;

import com.japanese.lessons.models.lesson.mangaExercise.MangaPhotoDescription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMangaPhotoDescriptionRepository extends JpaRepository<MangaPhotoDescription, Long> {
}
