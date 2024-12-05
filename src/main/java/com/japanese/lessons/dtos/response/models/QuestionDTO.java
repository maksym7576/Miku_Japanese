package com.japanese.lessons.dtos.response.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDTO {

    private Long questionId;
    private String question;
    private String description;
    private Long correctAnswerID;

}
