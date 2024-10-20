package com.japanese.lessons.repositories.Lesson;

import com.japanese.lessons.models.Lesson.Answer;
import org.springframework.data.repository.CrudRepository;

public interface IAnswerRepository extends CrudRepository<Answer, Long> {
}
