package com.japanese.lessons.repositories;

import com.japanese.lessons.models.LessonMicroservice.Lesson;
import org.springframework.data.repository.CrudRepository;

public interface ILessonRepository extends CrudRepository<Lesson, Long> {
}
