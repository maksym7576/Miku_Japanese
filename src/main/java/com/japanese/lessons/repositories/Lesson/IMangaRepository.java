package com.japanese.lessons.repositories.Lesson;

import com.japanese.lessons.models.Manga;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IMangaRepository extends CrudRepository<Manga, Long> {
    Optional<Manga> findByLessonId(Long lessonId);
}
