package com.japanese.lessons.service.LessonMicroservice.manga;

import com.japanese.lessons.dtos.request.MangaRequest;
import com.japanese.lessons.models.LessonMicroservice.manga.Manga;
import com.japanese.lessons.models.LessonMicroservice.manga.MangaDialogue;
import com.japanese.lessons.models.LessonMicroservice.test.Answer;
import com.japanese.lessons.models.LessonMicroservice.test.Question;
import com.japanese.lessons.repositories.LessonMisroservice.IMangaRepository;
import com.japanese.lessons.service.LessonMicroservice.test.AnswerService;
import com.japanese.lessons.service.LessonMicroservice.test.QuestionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MangaService {

    @Autowired
    IMangaRepository iMangaRepository;

    @Autowired
    MangaDialogueService mangaDialogueService;

    @Autowired
    QuestionService questionService;

    @Autowired
    AnswerService answerService;


    public Manga getMangaById(Long id) {
        return iMangaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Has occurred an error with manga"));
    }
//    public Manga sortFiles() {
//
//    }

    @Transactional
    public void createManga(MangaRequest mangaRequest) {
        Manga manga = mangaRequest.getManga();
        List<MangaDialogue> mangaDialogueList = mangaRequest.getMangaTextList();
        List<Question> questionList = mangaRequest.getQuestionList();
        List<Answer> answerList = mangaRequest.getAnswerList();

        Manga savedManga = saveManga(manga);

        if (!mangaDialogueList.isEmpty()) {
            mangaDialogueList.forEach(mangaText -> {
                mangaText.setManga(savedManga);
            });
            mangaDialogueService.saveAllMangaDialogues(mangaDialogueList);
        }
        if (!questionList.isEmpty()) {
            questionList.forEach(question -> {
                question.setManga(savedManga);
            });
            List<Question> savedQuestions = questionService.saveAllQuestions(questionList);

            if (!answerList.isEmpty()) {
                answerList.forEach(answer -> {
                    Question matchingQuestion = savedQuestions.stream()
                            .filter(q -> q.getTurn() == answer.getTurn())
                            .findFirst()
                            .orElse(null);

                    if (matchingQuestion != null) {
                        answer.setQuestion(matchingQuestion);
                    }
                });
                answerService.saveAllAnswers(answerList);
            }
        }
    }

    public Manga saveManga(Manga manga) {
        if (manga == null) {
            throw new IllegalArgumentException("Has occurred an error when saving Manga");
        }
        return iMangaRepository.save(manga);
    }
    

}
