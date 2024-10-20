package com.japanese.lessons.repositories.Lesson;

import com.japanese.lessons.models.Lesson.Question;
import org.springframework.data.repository.CrudRepository;

public interface IQuestionRepository extends CrudRepository<Question, Long> {
}
