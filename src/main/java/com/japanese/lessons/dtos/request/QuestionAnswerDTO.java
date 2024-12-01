package com.japanese.lessons.dtos.request;

import com.japanese.lessons.models.Image;
import com.japanese.lessons.models.lesson.exercise.Question;
import com.japanese.lessons.models.lesson.mangaExercise.Text;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class QuestionAnswerDTO {

    private Question question;
    private List<Text> answerList;
    private Image image;
}
