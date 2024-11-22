package com.japanese.lessons.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseEvaluationDTO {

    private Boolean isCorrect;
    private Object correctAnswer;
    private String description;
}
