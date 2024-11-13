package com.japanese.lessons.service;

import com.japanese.lessons.models.ETargetType;
import com.japanese.lessons.models.Ordered_objects;
import com.japanese.lessons.repositories.Lesson.IOrdered_objects_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class Ordered_objects_service {

    @Autowired private IOrdered_objects_repository iOrderedObjectsRepository;

    public List<Ordered_objects> getOrderedObjectsListByOrderedIdAndType(ETargetType eTargetType, Long parentObjectId) {
        List<Ordered_objects> orderedObjectsList = iOrderedObjectsRepository.findByParentObjectTypeAndParentObjectId(eTargetType, parentObjectId);
        return orderedObjectsList != null ? orderedObjectsList : Collections.emptyList();
    }

}
