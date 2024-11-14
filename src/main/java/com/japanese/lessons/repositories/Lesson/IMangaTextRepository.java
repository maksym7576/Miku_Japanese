package com.japanese.lessons.repositories.Lesson;

import com.japanese.lessons.models.ETargetType;
import com.japanese.lessons.models.lesson.mangaExercise.Text;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IMangaTextRepository extends JpaRepository<Text, Long> {
    List<Text> findAllByTargetTypeAndTargetId(ETargetType targetType, Long targetId);
    Text findByTargetTypeAndTargetId(ETargetType targetType, Long targetId);
}
