package com.japanese.lessons.service;

import com.japanese.lessons.models.LessonMicroservice.Lesson;
import com.japanese.lessons.repositories.ILessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LessonService {

    @Autowired
    ILessonRepository iLessonRepository;

    public Lesson getLessonById(Long id) {
        return iLessonRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Lesson is not exists: " + id));
    }

    public void saveLesson(Lesson lesson) {
        if(lesson == null) {
            throw new IllegalArgumentException("Lesson is null");
        } else {
            lesson.validateCompletion();
            iLessonRepository.save(lesson);
        }
    }
}
