package com.japanese.lessons.service.LessonMicroservice.test;
import com.japanese.lessons.models.LessonMicroservice.test.Answer;
import com.japanese.lessons.models.LessonMicroservice.test.Question;
import com.japanese.lessons.models.LessonMicroservice.manga.Manga;
import com.japanese.lessons.repositories.test.IQuestionRepository;
import com.japanese.lessons.service.LessonMicroservice.test.QuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuestionServiceTest {

    @Mock
    IQuestionRepository iQuestionRepository;

    @InjectMocks
    QuestionService questionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveAllQuestions_ShouldSaveAllQuestionsWhenValid() {
        Question question1 = new Question();
        question1.setId(1L);
        question1.setTurn(1);
        question1.setQuestion("What is the capital of Japan?");
        question1.setCorrectAnswer("Tokyo");
        question1.setAnswers(Collections.singletonList(new Answer()));
        question1.setManga(new Manga());

        Question question2 = new Question();
        question2.setId(2L);
        question2.setTurn(2);
        question2.setQuestion("What is the national sport of Japan?");
        question2.setCorrectAnswer("Sumo");
        question2.setAnswers(Collections.singletonList(new Answer()));
        question2.setManga(new Manga());

        List<Question> questionList = Arrays.asList(question1, question2);

        when(iQuestionRepository.saveAll(questionList)).thenReturn(questionList);

        List<Question> savedQuestions = questionService.saveAllQuestions(questionList);

        verify(iQuestionRepository, times(1)).saveAll(questionList);
        assertEquals(2, savedQuestions.size());
    }

    @Test
    void saveAllQuestions_ShouldThrowExceptionWhenQuestionIncomplete() {
        Question question1 = new Question();
        question1.setId(1L);
        question1.setTurn(0);
        question1.setQuestion("Incomplete question");

        List<Question> questionList = Arrays.asList(question1);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            questionService.saveAllQuestions(questionList);
        });
        assertEquals("Question no all data is completed: " + question1.getId(), exception.getMessage());
    }

    @Test
    void saveQuestion_ShouldSaveQuestionWhenComplete() {
        Question question = new Question();
        question.setId(1L);
        question.setTurn(1);
        question.setQuestion("What is the capital of Japan?");
        question.setCorrectAnswer("Tokyo");
        question.setAnswers(Collections.singletonList(new Answer()));
        question.setManga(new Manga());

        questionService.saveQuestion(question);

        verify(iQuestionRepository, times(1)).save(question);
    }

    @Test
    void saveQuestion_ShouldThrowExceptionWhenIncomplete() {
        Question question = new Question();
        question.setId(1L);
        question.setTurn(0);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            questionService.saveQuestion(question);
        });
        assertEquals("Question no all data is completed.", exception.getMessage());
    }

    @Test
    void updateQuestion_ShouldUpdateQuestionWhenExists() {
        Long id = 1L;
        Question existingQuestion = new Question();
        existingQuestion.setId(id);
        existingQuestion.setTurn(1);
        existingQuestion.setQuestion("Existing question");
        existingQuestion.setCorrectAnswer("Correct");

        Question updatedQuestion = new Question();
        updatedQuestion.setTurn(2);
        updatedQuestion.setQuestion("Updated question");

        when(iQuestionRepository.findById(id)).thenReturn(Optional.of(existingQuestion));

        questionService.updateQuestion(id, updatedQuestion);

        verify(iQuestionRepository, times(1)).save(existingQuestion);
        assertEquals(2, existingQuestion.getTurn());
        assertEquals("Updated question", existingQuestion.getQuestion());
    }

    @Test
    void updateQuestion_ShouldThrowExceptionWhenQuestionDoesNotExist() {
        Long id = 1L;
        Question updatedQuestion = new Question();
        when(iQuestionRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            questionService.updateQuestion(id, updatedQuestion);
        });
        assertEquals("This question isn't exists", exception.getMessage());
    }

    @Test
    void delete_question_success() {
        Long id = 1L;
        when(iQuestionRepository.existsById(id)).thenReturn(true);

        questionService.deleteQuestion(id);

        verify(iQuestionRepository, times(1)).deleteById(id);
    }

    @Test
    void delete_test_error_if_not_exist() {
        Long id = 1L;
        when(iQuestionRepository.existsById(id)).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            questionService.deleteQuestion(id);
        });
        assertEquals("Question isn't exists", exception.getMessage());
    }
}
