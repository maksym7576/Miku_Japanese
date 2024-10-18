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
            throw new IllegalArgumentException("Answer cannot have null.");
        }
    }
    public void saveAnswer(Answer answer) {
        if (answer != null) {
            iAnswerRepository.save(answer);
        } else {
            throw new IllegalArgumentException("Answer cannot have empty test.");
        }
    }
    public void deleteAnswer(Long id) {
        if(iAnswerRepository.existsById(id)) {
            iAnswerRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Answer isn't exists");
        }
    }
    public void updateAnswer(Long id, Answer answer) {
        if(iAnswerRepository.existsById(id)) {
            answer.setId(id);
            iAnswerRepository.save(answer);
        } else {
            throw new IllegalArgumentException("Answer isn't exists");
        }
    }
}
