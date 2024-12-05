package com.japanese.lessons.repositories.Lesson;

import com.japanese.lessons.models.fourth.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IQuestionRepository extends JpaRepository<Question, Long> {
}
