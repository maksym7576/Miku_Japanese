package com.japanese.lessons.repositories.LessonMisroservice;

import com.japanese.lessons.models.LessonMicroservice.manga.MangaDialogue;
import org.springframework.data.repository.CrudRepository;

public interface IMangaTextRepository extends CrudRepository<MangaDialogue, Long> {
}
