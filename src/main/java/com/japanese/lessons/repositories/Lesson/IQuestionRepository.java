package com.japanese.lessons.repositories.Lesson;

import com.japanese.lessons.models.ETargetType;
import com.japanese.lessons.models.lesson.mangaExercise.QuestionManga;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IQuestionRepository extends JpaRepository<QuestionManga, Long> {
    List<QuestionManga> findByTargetTypeAndTargetId(ETargetType targetType, Long targetId);
}
