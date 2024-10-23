package com.japanese.lessons.repositories.Lesson;

import com.japanese.lessons.models.lesson.Manga;
import org.springframework.data.repository.CrudRepository;

public interface IMangaRepository extends CrudRepository<Manga, Long> {
}
