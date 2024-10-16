package com.japanese.lessons.repositories.LessonMisroservice;

import com.japanese.lessons.models.LessonMicroservice.manga.MangaText;
import org.springframework.data.repository.CrudRepository;

public interface IMangaTextRepository extends CrudRepository<MangaText, Long> {
}
