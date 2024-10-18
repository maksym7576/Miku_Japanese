package com.japanese.lessons.dtos.request;

import com.japanese.lessons.models.LessonMicroservice.manga.Manga;
import com.japanese.lessons.models.LessonMicroservice.manga.Manga_dialogs;
import com.japanese.lessons.models.LessonMicroservice.test.Quiz_answers;
import com.japanese.lessons.models.LessonMicroservice.test.Question;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MangaRequest {
    private Manga manga;
    private List<Manga_dialogs> mangadialogsList = new ArrayList<>();
    private List<Question> questionList = new ArrayList<>();
    private List<Quiz_answers> quizanswersList = new ArrayList<>();
}
