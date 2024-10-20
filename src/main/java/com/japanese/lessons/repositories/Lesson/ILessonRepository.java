package com.japanese.lessons.repositories.Lesson;

import com.japanese.lessons.models.Lesson.Lesson;
import org.springframework.data.repository.CrudRepository;

public interface ILessonRepository extends CrudRepository<Lesson, Long> {
}
