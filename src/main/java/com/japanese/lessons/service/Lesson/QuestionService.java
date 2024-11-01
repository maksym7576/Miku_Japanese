package com.japanese.lessons.service.Lesson;

import com.japanese.lessons.models.lesson.mangaExercise.QuestionManga;
import com.japanese.lessons.repositories.Lesson.IQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class QuestionService {

    @Autowired
    IQuestionRepository iQuestionRepository;

    public List<QuestionManga> saveAllQuestions(List<QuestionManga> questionMangaList) {
        for(QuestionManga questionManga : questionMangaList) {
            validQuestionIsNull(questionManga);
            questionManga.validateCompletion();
        }
            Iterable<QuestionManga> savedQuestions = iQuestionRepository.saveAll(questionMangaList);
            return StreamSupport.stream(savedQuestions.spliterator(), false)
                    .collect(Collectors.toList());
    }

    public void saveQuestion(QuestionManga questionManga) {
        validQuestionIsNull(questionManga);
        questionManga.validateCompletion();
        iQuestionRepository.save(questionManga);
    }

    private void validQuestionIsNull(QuestionManga questionManga) {
        if(questionManga == null) {
            throw new IllegalArgumentException("Question is null: " + questionManga.getId());
        }
    }

    public void deleteQuestion(Long id) {
        if(iQuestionRepository.existsById(id)) {
            iQuestionRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Question isn't exists");
        }
    }
    public void updateQuestion(Long id, QuestionManga updatedQuestionManga) {
      QuestionManga responceQuestionManga = getQuestionById(id);
      responceQuestionManga.copyNonNullProperties(updatedQuestionManga);
      iQuestionRepository.save(responceQuestionManga);
    }
    private QuestionManga getQuestionById(Long id) {
        return iQuestionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("This question isn't exists"));
    }
}
