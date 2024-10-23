package com.japanese.lessons.repositories.Lesson;

import com.japanese.lessons.models.lesson.MangaDialogue;
import org.springframework.data.repository.CrudRepository;

public interface IMangaTextRepository extends CrudRepository<MangaDialogue, Long> {
}
