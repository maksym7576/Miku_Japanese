package com.japanese.lessons.repositories.Lesson;

import com.japanese.lessons.models.ETargetType;
import com.japanese.lessons.models.lesson.mangaExercise.MangaDialogue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IMangaTextRepository extends JpaRepository<MangaDialogue, Long> {
    List<MangaDialogue> findByTargetTypeAndTargetId(ETargetType targetType, Long targetId);
}
