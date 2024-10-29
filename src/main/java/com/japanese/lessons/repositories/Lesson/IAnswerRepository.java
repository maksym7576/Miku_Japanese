package com.japanese.lessons.repositories.Lesson;

import com.japanese.lessons.models.lesson.mangaExercise.AnswerManga;
import org.springframework.data.repository.CrudRepository;

public interface IAnswerRepository extends CrudRepository<AnswerManga, Long> {
}
