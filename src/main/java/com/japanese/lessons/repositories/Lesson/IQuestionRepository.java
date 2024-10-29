package com.japanese.lessons.repositories.Lesson;

import com.japanese.lessons.models.lesson.mangaExercise.QuestionManga;
import org.springframework.data.repository.CrudRepository;

public interface IQuestionRepository extends CrudRepository<QuestionManga, Long> {
}
