package com.japanese.lessons.repositories;

import com.japanese.lessons.models.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IExerciseRepository extends JpaRepository<Exercise, Long> {
    Optional<Exercise> findByLessonId(Long lessonId);
}
