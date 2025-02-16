package com.japanese.lessons.dtos;

import com.japanese.lessons.dtos.response.models.QuestionDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NovelQuestionDTO {

    private QuestionWithAnswerDTO question;
    private List<StructuredDataForExercisesDTO> phrasesFalseLine;
}
