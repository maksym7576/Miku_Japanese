package com.japanese.lessons.service.Lesson;

import com.japanese.lessons.models.lesson.Answer;
import com.japanese.lessons.models.lesson.Question;
import com.japanese.lessons.repositories.Lesson.IAnswerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AnswerServiceTest {

    private MockMvc mockMvc;

    @Mock
    IAnswerRepository iAnswerRepository;

    @InjectMocks
    AnswerService answerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveAllAnswers() {
        List<Answer> answerList = new ArrayList<>();
        Answer answer = new Answer();
        answerList.add(answer);
        when(iAnswerRepository.saveAll(answerList)).thenReturn(answerList);

        answerService.saveAllAnswers(answerList);

        verify(iAnswerRepository, times(1)).saveAll(answerList);
    }

    @Test
    void save_answer_success() {
     Question question = new Question();
     Long id = 1L;
     Answer answer = new Answer();
     answer.setId(id);
     answer.setTurn(1);
     answer.setIsCorrect(true);
     answer.setQuestion(question);
     answer.setAnswer("answer");

     when(iAnswerRepository.save(answer)).thenReturn(answer);

     answerService.saveAnswer(answer);

     verify(iAnswerRepository, times(1)).save(answer);
    }
    @Test
    void save_answer_error_not_all_data_is_full() {
        Answer answer = new Answer();
        answer.setId(1L);

        when(iAnswerRepository.save(answer)).thenReturn(answer);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            answerService.saveAnswer(answer);
        });

        assertEquals("No all data is completed.", exception.getMessage());
    }
    @Test
    void save_answer_error_is_null() {

        when(iAnswerRepository.save(null)).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            answerService.saveAnswer(null);
        });

        assertEquals("Answer is null", exception.getMessage());
    }

    @Test
    void delete_answer_success() {
        Long id = 1L;
        when(iAnswerRepository.existsById(id)).thenReturn(true);

        answerService.deleteAnswer(id);

        verify(iAnswerRepository, times(1)).deleteById(id);
    }
    @Test
    void delete_answer_error_is_not_exist() {
        Long id = 1L;

        when(iAnswerRepository.existsById(id)).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            answerService.deleteAnswer(id);
        });
        assertEquals("Answer isn't exists", exception.getMessage());
    }

    @Test
    void update_answer_success() {
        Question question = new Question();
        Long id = 1L;
        Answer answer = new Answer();
        answer.setId(id);
        answer.setTurn(1);
        answer.setIsCorrect(true);
        answer.setQuestion(question);
        answer.setAnswer("answer");

        when(iAnswerRepository.findById(id)).thenReturn(Optional.of(answer));
        when(iAnswerRepository.save(answer)).thenReturn(answer);

        answerService.updateAnswer(id, answer);

        verify(iAnswerRepository, times(1)).save(answer);
    }

    @Test
    void get_answer_by_id_success() {
        Answer answer = new Answer();
        answer.setId(1L);

        when(iAnswerRepository.findById(1L)).thenReturn(Optional.of(answer));

        Answer response = answerService.getAnswerById(1L);
        assertEquals(answer,response);
        verify(iAnswerRepository, times(1)).findById(1L);
    }
    @Test
    void get_answer_by_id_is_not_exist() {
        Long id = 1L;

        when(iAnswerRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            answerService.getAnswerById(id);
        });
        assertEquals("Answer not found with id: " + id, exception.getMessage());
    }
}