package com.japanese.lessons.service.LessonMicroservice.test;

import com.japanese.lessons.models.LessonMicroservice.test.Quiz_answers;
import com.japanese.lessons.repositories.test.IAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService {

    @Autowired
    IAnswerRepository iAnswerRepository;

    public void saveAllAnswers(List<Quiz_answers> quizanswersList) {
        if(quizanswersList != null && !quizanswersList.isEmpty()) {
            iAnswerRepository.saveAll(quizanswersList);
        } else {
            throw new IllegalArgumentException("Answer cannot have null.");
        }
    }
    public void saveAnswer(Quiz_answers quizanswers) {
        if (quizanswers != null) {
            iAnswerRepository.save(quizanswers);
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
    public void updateAnswer(Long id, Quiz_answers quizanswers) {
        if(iAnswerRepository.existsById(id)) {
            quizanswers.setId(id);
            iAnswerRepository.save(quizanswers);
        } else {
            throw new IllegalArgumentException("Answer isn't exists");
        }
    }
}
