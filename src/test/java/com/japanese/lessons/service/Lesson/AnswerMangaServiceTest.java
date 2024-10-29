package com.japanese.lessons.service.Lesson;

import com.japanese.lessons.models.lesson.mangaExercise.AnswerManga;
import com.japanese.lessons.models.lesson.mangaExercise.QuestionManga;
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

class AnswerMangaServiceTest {

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
        List<AnswerManga> answerMangaList = new ArrayList<>();
        AnswerManga answerManga = new AnswerManga();
        answerMangaList.add(answerManga);
        when(iAnswerRepository.saveAll(answerMangaList)).thenReturn(answerMangaList);

        answerService.saveAllAnswers(answerMangaList);

        verify(iAnswerRepository, times(1)).saveAll(answerMangaList);
    }

    @Test
    void save_answer_success() {
     QuestionManga questionManga = new QuestionManga();
     Long id = 1L;
     AnswerManga answerManga = new AnswerManga();
     answerManga.setId(id);
     answerManga.setTurn(1);
     answerManga.setIsCorrect(true);
     answerManga.setQuestionManga(questionManga);
     answerManga.setAnswer("answer");

     when(iAnswerRepository.save(answerManga)).thenReturn(answerManga);

     answerService.saveAnswer(answerManga);

     verify(iAnswerRepository, times(1)).save(answerManga);
    }
    @Test
    void save_answer_error_not_all_data_is_full() {
        AnswerManga answerManga = new AnswerManga();
        answerManga.setId(1L);

        when(iAnswerRepository.save(answerManga)).thenReturn(answerManga);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            answerService.saveAnswer(answerManga);
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
        QuestionManga questionManga = new QuestionManga();
        Long id = 1L;
        AnswerManga answerManga = new AnswerManga();
        answerManga.setId(id);
        answerManga.setTurn(1);
        answerManga.setIsCorrect(true);
        answerManga.setQuestionManga(questionManga);
        answerManga.setAnswer("answer");

        when(iAnswerRepository.findById(id)).thenReturn(Optional.of(answerManga));
        when(iAnswerRepository.save(answerManga)).thenReturn(answerManga);

        answerService.updateAnswer(id, answerManga);

        verify(iAnswerRepository, times(1)).save(answerManga);
    }

    @Test
    void get_answer_by_id_success() {
        AnswerManga answerManga = new AnswerManga();
        answerManga.setId(1L);

        when(iAnswerRepository.findById(1L)).thenReturn(Optional.of(answerManga));

        AnswerManga response = answerService.getAnswerById(1L);
        assertEquals(answerManga,response);
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