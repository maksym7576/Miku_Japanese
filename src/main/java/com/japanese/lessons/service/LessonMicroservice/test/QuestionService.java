package com.japanese.lessons.service.LessonMicroservice.test;

import com.japanese.lessons.models.LessonMicroservice.test.Question;
import com.japanese.lessons.repositories.test.IQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    IQuestionRepository iQuestionRepository;

    public void saveAllQuestions(List<Question> questionList) {
        if(questionList != null && !questionList.isEmpty()) {
            iQuestionRepository.saveAll(questionList);
        } else {
            throw new IllegalArgumentException("Has occurred an error with saving questionList");
        }
    }

    public void saveQuestion(Question question) {
        if(question != null) {
            iQuestionRepository.save(question);
        } else {
            throw new IllegalArgumentException("Has occurred an error with saving question");
        }
    }
}
