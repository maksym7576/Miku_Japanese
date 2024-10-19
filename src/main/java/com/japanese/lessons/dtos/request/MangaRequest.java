package com.japanese.lessons.dtos.request;

import com.japanese.lessons.models.LessonMicroservice.manga.Manga;
import com.japanese.lessons.models.LessonMicroservice.manga.MangaDialogue;
import com.japanese.lessons.models.LessonMicroservice.test.Answer;
import com.japanese.lessons.models.LessonMicroservice.test.Question;

import java.util.ArrayList;
import java.util.List;

public class MangaRequest {
    private Manga manga;
    private List<MangaDialogue> mangaDialogueList = new ArrayList<>();
    private List<Question> questionList = new ArrayList<>();
    private List<Answer> answerList = new ArrayList<>();

    public Manga getManga() {
        return manga;
    }

    public void setManga(Manga manga) {
        this.manga = manga;
    }

    public List<MangaDialogue> getMangaTextList() {
        return mangaDialogueList;
    }

    public void setMangaTextList(List<MangaDialogue> mangaDialogueList) {
        this.mangaDialogueList = mangaDialogueList;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }

    public List<Answer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<Answer> answerList) {
        this.answerList = answerList;
    }
}
