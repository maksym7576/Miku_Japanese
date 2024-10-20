package com.japanese.lessons.repositories.Lesson;

import com.japanese.lessons.models.Lesson.MangaDialogue;
import org.springframework.data.repository.CrudRepository;

public interface IMangaTextRepository extends CrudRepository<MangaDialogue, Long> {
}
