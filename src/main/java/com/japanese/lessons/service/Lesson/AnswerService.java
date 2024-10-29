package com.japanese.lessons.service.Lesson;

import com.japanese.lessons.models.lesson.mangaExercise.AnswerManga;
import com.japanese.lessons.repositories.Lesson.IAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService {

    @Autowired
    IAnswerRepository iAnswerRepository;

    public AnswerManga getAnswerById(Long id) {
      return iAnswerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Answer not found with id: " + id));
    }
    public void saveAllAnswers(List<AnswerManga> answerMangaList) {
            iAnswerRepository.saveAll(answerMangaList);
    }
    public void saveAnswer(AnswerManga answerManga) {
        validAnswerIsNull(answerManga);
        if (answerManga.isComplete()) {
            iAnswerRepository.save(answerManga);
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
    private void validAnswerIsNull(AnswerManga answerManga) {
        if(answerManga == null) {
            throw new IllegalArgumentException("Answer is null");
        }
    }

    public void updateAnswer(Long id, AnswerManga answerManga) {
            AnswerManga responseAnswerManga = getAnswerById(id);
            responseAnswerManga.copyNonNullProperties(answerManga);
            saveAnswer(responseAnswerManga);
    }
}
