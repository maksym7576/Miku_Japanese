package com.japanese.lessons.service.LessonMicroservice.test;

import com.japanese.lessons.models.LessonMicroservice.test.Question;
import com.japanese.lessons.repositories.test.IQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class QuestionService {

    @Autowired
    IQuestionRepository iQuestionRepository;

    public List<Question> saveAllQuestions(List<Question> questionList) {
        for(Question question : questionList) {
            validQuestionIsNull(question);
            if(!question.isComplete()) {
                throw new IllegalArgumentException("Question no all data is completed: " + question.getId());
            }
        }
            Iterable<Question> savedQuestions = iQuestionRepository.saveAll(questionList);
            return StreamSupport.stream(savedQuestions.spliterator(), false)
                    .collect(Collectors.toList());
    }

    public void saveQuestion(Question question) {
        validQuestionIsNull(question);
        if(question.isComplete()) {
            iQuestionRepository.save(question);
        } else {
            throw new IllegalArgumentException("Question no all data is completed.");
        }
    }

    private void validQuestionIsNull(Question question) {
        if(question == null) {
            throw new IllegalArgumentException("Question is null: " + question.getId());
        }
    }

    public void deleteQuestion(Long id) {
        if(iQuestionRepository.existsById(id)) {
            iQuestionRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Question isn't exists");
        }
    }
    public void updateQuestion(Long id, Question updatedQuestion) {
      Question responceQuestion = getQuestionById(id);
      responceQuestion.copyNonNullProperties(updatedQuestion);
      iQuestionRepository.save(responceQuestion);
    }
    private Question getQuestionById(Long id) {
        return iQuestionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("This question isn't exists"));
    }

}
