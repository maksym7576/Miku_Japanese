package com.japanese.lessons.repositories.Lesson;

import com.japanese.lessons.models.third.Ordered_objects;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOrdered_objects_repository extends JpaRepository<Ordered_objects, Long> {
    List<Ordered_objects> findByExercise_id(Long exerciseId);
}
