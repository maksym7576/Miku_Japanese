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

    @Autowired
    AnswerService answerService;

    public List<Question> saveAllQuestions(List<Question> questionList) {
        if(questionList != null && !questionList.isEmpty()) {
            Iterable<Question> savedQuestions = iQuestionRepository.saveAll(questionList);
            return StreamSupport.stream(savedQuestions.spliterator(), false)
                    .collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException("QuestionList cannot have null.");
        }
    }

    public void saveQuestion(Question question) {
        if(question != null) {
            iQuestionRepository.save(question);
        } else {
            throw new IllegalArgumentException("Question is empty.");
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

      responceQuestion.setQuestion(updatedQuestion.getQuestion());
      responceQuestion.setTurn(updatedQuestion.getTurn());
      iQuestionRepository.save(responceQuestion);
    }
    private Question getQuestionById(Long id) {
        return iQuestionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("This question isn't exists"));
    }

}
