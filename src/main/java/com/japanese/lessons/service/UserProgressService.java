package com.japanese.lessons.service;

import com.japanese.lessons.models.User.EFinishedTypes;
import com.japanese.lessons.models.User.UserProgress;
import com.japanese.lessons.repositories.IUserProgressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProgressService {

    @Autowired private IUserProgressRepository iUserProgressRepository;

//    public List<UserProgress> getAllUserProgressLessonsByETypeAndUserId(EFinishedTypes eFinishedTypes, Long userId) {
//        return iUserProgressRepository.findByEFinishedTypesAndUserId(eFinishedTypes, userId);
//    }

    public List<UserProgress> getAllUserProgressExercisesBuETypeAndLessonIdAndUserId(EFinishedTypes eFinishedTypes, Long lessonId, Long userId) {
        return iUserProgressRepository.findByEFinishedTypesAndLessonIdAndUserId(eFinishedTypes, lessonId, userId);
    }
}
