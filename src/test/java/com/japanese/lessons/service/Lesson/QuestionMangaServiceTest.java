package com.japanese.lessons.service.Lesson;
import com.japanese.lessons.models.lesson.mangaExercise.AnswerManga;
import com.japanese.lessons.models.lesson.mangaExercise.QuestionManga;
import com.japanese.lessons.models.lesson.mangaExercise.Manga;
import com.japanese.lessons.repositories.Lesson.IQuestionRepository;
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

class QuestionMangaServiceTest {

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
        QuestionManga questionManga1 = new QuestionManga();
        questionManga1.setId(1L);
        questionManga1.setQueue(1);
        questionManga1.setQuestion("What is the capital of Japan?");
        questionManga1.setCorrect_answer_hiragana_katakana_kanji("Tokyo");
        questionManga1.setAnswerMangas(Collections.singletonList(new AnswerManga()));
        questionManga1.setManga(new Manga());

        QuestionManga questionManga2 = new QuestionManga();
        questionManga2.setId(2L);
        questionManga2.setQueue(2);
        questionManga2.setQuestion("What is the national sport of Japan?");
        questionManga2.setCorrect_answer_hiragana_katakana_kanji("Sumo");
        questionManga2.setAnswerMangas(Collections.singletonList(new AnswerManga()));
        questionManga2.setManga(new Manga());

        List<QuestionManga> questionMangaList = Arrays.asList(questionManga1, questionManga2);

        when(iQuestionRepository.saveAll(questionMangaList)).thenReturn(questionMangaList);

        List<QuestionManga> savedQuestionMangas = questionService.saveAllQuestions(questionMangaList);

        verify(iQuestionRepository, times(1)).saveAll(questionMangaList);
        assertEquals(2, savedQuestionMangas.size());
    }

    @Test
    void saveAllQuestions_ShouldThrowExceptionWhenQuestionIncomplete() {
        QuestionManga questionManga1 = new QuestionManga();
        questionManga1.setId(1L);
        questionManga1.setQueue(0);
        questionManga1.setQuestion("Incomplete question");

        List<QuestionManga> questionMangaList = Arrays.asList(questionManga1);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            questionService.saveAllQuestions(questionMangaList);
        });
        assertEquals("Question no all data is completed: " + questionManga1.getId(), exception.getMessage());
    }

    @Test
    void saveQuestion_ShouldSaveQuestionWhenComplete() {
        QuestionManga questionManga = new QuestionManga();
        questionManga.setId(1L);
        questionManga.setQueue(1);
        questionManga.setQuestion("What is the capital of Japan?");
        questionManga.setCorrect_answer_hiragana_katakana_kanji("Tokyo");
        questionManga.setAnswerMangas(Collections.singletonList(new AnswerManga()));
        questionManga.setManga(new Manga());

        questionService.saveQuestion(questionManga);

        verify(iQuestionRepository, times(1)).save(questionManga);
    }

    @Test
    void saveQuestion_ShouldThrowExceptionWhenIncomplete() {
        QuestionManga questionManga = new QuestionManga();
        questionManga.setId(1L);
        questionManga.setQueue(0);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            questionService.saveQuestion(questionManga);
        });
        assertEquals("Question no all data is completed.", exception.getMessage());
    }

    @Test
    void updateQuestion_ShouldUpdateQuestionWhenExists() {
        Long id = 1L;
        QuestionManga existingQuestionManga = new QuestionManga();
        existingQuestionManga.setId(id);
        existingQuestionManga.setQueue(1);
        existingQuestionManga.setQuestion("Existing question");
        existingQuestionManga.setCorrect_answer_hiragana_katakana_kanji("Correct");

        QuestionManga updatedQuestionManga = new QuestionManga();
        updatedQuestionManga.setQueue(2);
        updatedQuestionManga.setQuestion("Updated question");

        when(iQuestionRepository.findById(id)).thenReturn(Optional.of(existingQuestionManga));

        questionService.updateQuestion(id, updatedQuestionManga);

        verify(iQuestionRepository, times(1)).save(existingQuestionManga);
        assertEquals(2, existingQuestionManga.getQueue());
        assertEquals("Updated question", existingQuestionManga.getQuestion());
    }

    @Test
    void updateQuestion_ShouldThrowExceptionWhenQuestionDoesNotExist() {
        Long id = 1L;
        QuestionManga updatedQuestionManga = new QuestionManga();
        when(iQuestionRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            questionService.updateQuestion(id, updatedQuestionManga);
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
