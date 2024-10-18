package com.japanese.lessons.repositories.test;

import com.japanese.lessons.models.LessonMicroservice.test.Quiz_answers;
import org.springframework.data.repository.CrudRepository;

public interface IAnswerRepository extends CrudRepository<Quiz_answers, Long> {
}
