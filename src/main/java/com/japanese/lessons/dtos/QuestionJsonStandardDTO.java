package com.japanese.lessons.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionJsonStandardDTO {

    private Long textId;
    private boolean isCorrect;
}
