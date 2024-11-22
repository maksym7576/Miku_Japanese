package com.japanese.lessons.dtos.request;

import com.japanese.lessons.models.User.UserIncorrectAnswers;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class AnswersDTO {
    private String type;
    private Long answerId;
    private boolean isCorrect;
}
