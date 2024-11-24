package com.japanese.lessons.repositories;

import com.japanese.lessons.models.lesson.exercise.Guidance;
import org.springframework.data.repository.CrudRepository;

public interface IGuidanceRepository extends CrudRepository<Guidance, Long> {
}
