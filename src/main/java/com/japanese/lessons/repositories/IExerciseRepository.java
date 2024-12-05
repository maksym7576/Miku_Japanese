package com.japanese.lessons.repositories;

import com.japanese.lessons.models.second.EExerciseType;
import com.japanese.lessons.models.second.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IExerciseRepository extends JpaRepository<Exercise, Long> {
    @Query("SELECT e FROM Exercise e WHERE e.lesson.id = :lessonId AND e.eExerciseType = :eExerciseType")
    List<Exercise> findExercisesByLessonAndType(
            @Param("lessonId") Long lessonId,
            @Param("eExerciseType") EExerciseType eExerciseType
    );
}
