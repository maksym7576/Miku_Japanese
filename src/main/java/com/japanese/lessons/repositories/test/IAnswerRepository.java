package com.japanese.lessons.repositories.test;

import com.japanese.lessons.models.LessonMicroservice.test.Answer;
import org.springframework.data.repository.CrudRepository;

public interface IAnswerRepository extends CrudRepository<Answer, Long> {
}
