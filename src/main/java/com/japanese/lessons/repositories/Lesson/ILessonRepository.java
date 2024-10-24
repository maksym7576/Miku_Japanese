package com.japanese.lessons.repositories.Lesson;

import com.japanese.lessons.models.lesson.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ILessonRepository extends JpaRepository<Lesson, Long> {
}
