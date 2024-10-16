package com.japanese.lessons.service.LessonMicroservice.test;

import com.japanese.lessons.models.LessonMicroservice.test.Answer;
import com.japanese.lessons.repositories.test.IAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService {

    @Autowired
    IAnswerRepository iAnswerRepository;

    public void saveAllAnswers(List<Answer> answerList) {
        if(answerList != null && !answerList.isEmpty()) {
            iAnswerRepository.saveAll(answerList);
        } else {
            throw new IllegalArgumentException("Has occurred an error with saving answerList");
        }
    }
    public void saveAnswer(Answer answer) {
        if (answer != null) {
            iAnswerRepository.save(answer);
        } else {
            throw new IllegalArgumentException("Has occurred an error with saving answer");
        }
    }
}
