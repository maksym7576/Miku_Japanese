package com.japanese.lessons.repositories.LessonMisroservice;

import com.japanese.lessons.models.LessonMicroservice.manga.Manga_dialogs;
import org.springframework.data.repository.CrudRepository;

public interface IMangaTextRepository extends CrudRepository<Manga_dialogs, Long> {
}
