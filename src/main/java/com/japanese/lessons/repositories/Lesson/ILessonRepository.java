package com.japanese.lessons.repositories.Lesson;

import com.japanese.lessons.models.first.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ILessonRepository extends JpaRepository<Lesson, Long> {
}
