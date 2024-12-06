package com.japanese.lessons.dtos.response.models;

import com.japanese.lessons.dtos.AnswerDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDTO {

    private Long id;
    private String question;
    private String description;
    private List<AnswerDTO> answerList;

}
