package com.japanese.lessons.service.Lesson;

import com.japanese.lessons.models.lesson.Lesson;
import com.japanese.lessons.repositories.Lesson.ILessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<Lesson> getAllLessons() {
        try {
            List<Lesson> lessonList = (List<Lesson>) iLessonRepository.findAll();
            if (lessonList == null) {
                throw new Exception("Lessons not found");
            }
            return lessonList;
        } catch (Exception e) {
            throw new IllegalArgumentException("Error retrieving lessons", e);
        }
    }
    public List<Lesson> getAllSortedLessons() {
        try {
        List<Lesson> lessonList =  iLessonRepository.findAll(Sort.by(Sort.Order.desc("level"), Sort.Order.asc("position")));
        if(lessonList == null) {
            throw new Exception("Lessons not found");
        }
        return lessonList;
        } catch (Exception e) {
            throw new IllegalArgumentException("Error:", e);
        }
    }
}
