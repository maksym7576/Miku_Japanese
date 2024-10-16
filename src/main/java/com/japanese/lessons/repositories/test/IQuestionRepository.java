package com.japanese.lessons.repositories.test;

import com.japanese.lessons.models.LessonMicroservice.test.Question;
import org.springframework.data.repository.CrudRepository;

public interface IQuestionRepository extends CrudRepository<Question, Long> {
}
