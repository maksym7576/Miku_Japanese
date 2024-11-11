package com.japanese.lessons.service.Lesson;

import com.japanese.lessons.models.ETargetType;
import com.japanese.lessons.models.lesson.exercise.Question;
import com.japanese.lessons.repositories.Lesson.IQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
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
            question.validateCompletion();
        }
            Iterable<Question> savedQuestions = iQuestionRepository.saveAll(questionList);
            return StreamSupport.stream(savedQuestions.spliterator(), false)
                    .collect(Collectors.toList());
    }

    public void saveQuestion(Question question) {
        validQuestionIsNull(question);
        question.validateCompletion();
        iQuestionRepository.save(question);
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
    public Question getQuestionById(Long id) {
        return iQuestionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("This question isn't exists"));
    }

    public List<Question> getAllQuestionsByTypeAndObjectId(ETargetType targetType, Long objectId) {
        List<Question> response = iQuestionRepository.findByTargetTypeAndTargetId(targetType, objectId);

        return response != null ? response : Collections.emptyList();
    }
}
