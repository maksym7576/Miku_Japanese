package com.japanese.lessons.service;

import com.japanese.lessons.models.third.EActivityType;
import com.japanese.lessons.models.third.Ordered_objects;
import com.japanese.lessons.repositories.Lesson.IOrdered_objects_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class Ordered_objects_service {

    @Autowired private IOrdered_objects_repository iOrderedObjectsRepository;

    public List<Ordered_objects> getOrderedObjectsListByExerciseId(Long exerciseId) {
        List<Ordered_objects> orderedObjectsList = iOrderedObjectsRepository.findByExercise_id(exerciseId);
        return orderedObjectsList != null ? orderedObjectsList : Collections.emptyList();
    }

    public List<Ordered_objects> getOrderedObjectsListByEAcivityTypeAndExerciseIdsList(EActivityType eActivityType, List<Long> exerciseIdList) {
        List<Ordered_objects> orderedObjectsList = iOrderedObjectsRepository.findByActivityTypeAndExercise_IdIn(eActivityType, exerciseIdList);
        return orderedObjectsList != null ? orderedObjectsList : Collections.emptyList();
    }

}
