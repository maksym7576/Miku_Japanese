package com.japanese.lessons.service.Lesson;

import com.japanese.lessons.dtos.response.models.LessonDTO;
import com.japanese.lessons.models.User.EFinishedTypes;
import com.japanese.lessons.models.User.UserProgress;
import com.japanese.lessons.models.first.Lesson;
import com.japanese.lessons.repositories.Lesson.ILessonRepository;
import com.japanese.lessons.service.UserProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LessonService {

    @Autowired private ILessonRepository iLessonRepository;
    @Autowired private UserProgressService userProgressService;

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
        List<Lesson> lessonList =  iLessonRepository.findAll();
        if(lessonList == null) {
            throw new Exception("Lessons not found");
        }
        return lessonList;
        } catch (Exception e) {
            throw new IllegalArgumentException("Error:", e);
        }
    }

    private LessonDTO formLessonDTO(Lesson lesson, boolean isFinished) {
        return new LessonDTO(lesson.getId(), lesson.getPosition(), isFinished);
    }


    public List<LessonDTO> markIsFinishedInLessonList(Long userId, List<Lesson> lessonList) {
        List<LessonDTO> lessonDTOList = new ArrayList<>();
        List<UserProgress> userProgressList = userProgressService.getAllUserProgressLessonsByETypeAndUserId(EFinishedTypes.LESSON, userId);
        boolean isFinished = false;
        for (Lesson lesson : lessonList) {
            for (UserProgress userProgress : userProgressList) {
                if(lesson.getId() == userProgress.getObjectFinishedId()) {
                    isFinished = true;
                } else {
                    isFinished = false;
                }
            }
            lessonDTOList.add(formLessonDTO(lesson, isFinished));
        }
        return lessonDTOList;
    }
}
