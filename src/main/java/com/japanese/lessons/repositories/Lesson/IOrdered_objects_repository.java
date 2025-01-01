package com.japanese.lessons.repositories.Lesson;

import com.japanese.lessons.models.third.EActivityType;
import com.japanese.lessons.models.third.Ordered_objects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IOrdered_objects_repository extends JpaRepository<Ordered_objects, Long> {
    List<Ordered_objects> findByExercise_id(Long exerciseId);
    List<Ordered_objects> findByActivityTypeAndExercise_IdIn(EActivityType activityType, List<Long> exerciseIds);




}

