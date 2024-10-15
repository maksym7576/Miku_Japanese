package com.japanese.lessons.repositories.LessonMisroservice;

import com.japanese.lessons.models.LessonMicroservice.manga.Manga;
import org.springframework.data.repository.CrudRepository;

public interface IMangaRepository extends CrudRepository<Manga, Long> {
}
