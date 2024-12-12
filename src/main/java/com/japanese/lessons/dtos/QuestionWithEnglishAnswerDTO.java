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
public class QuestionWithEnglishAnswerDTO {

    private QuestionDTO question;
    private List<QuestionJsonEnglishTextDTO> questionAnswerList;
}
