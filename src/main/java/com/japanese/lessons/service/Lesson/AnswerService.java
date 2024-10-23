package com.japanese.lessons.service.Lesson;

import com.japanese.lessons.models.lesson.Answer;
import com.japanese.lessons.repositories.Lesson.IAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService {

    @Autowired
    IAnswerRepository iAnswerRepository;

    public Answer getAnswerById(Long id) {
      return iAnswerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Answer not found with id: " + id));
    }
    public void saveAllAnswers(List<Answer> answerList) {
            iAnswerRepository.saveAll(answerList);
    }
    public void saveAnswer(Answer answer) {
        validAnswerIsNull(answer);
        if (answer.isComplete()) {
            iAnswerRepository.save(answer);
        } else {
            throw new IllegalArgumentException("No all data is completed.");
        }
    }
    public void deleteAnswer(Long id) {
        if(iAnswerRepository.existsById(id)) {
            iAnswerRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Answer isn't exists");
        }
    }
    private void validAnswerIsNull(Answer answer) {
        if(answer == null) {
            throw new IllegalArgumentException("Answer is null");
        }
    }

    public void updateAnswer(Long id, Answer answer) {
            Answer responseAnswer = getAnswerById(id);
            responseAnswer.copyNonNullProperties(answer);
            saveAnswer(responseAnswer);
    }
}
