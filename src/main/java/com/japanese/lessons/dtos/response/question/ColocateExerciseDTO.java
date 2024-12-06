package com.japanese.lessons.dtos.response.question;

import com.japanese.lessons.dtos.response.models.QuestionDTO;
import com.japanese.lessons.models.fourth.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ColocateExerciseDTO {

    private QuestionDTO question;
    private ColocateWordsDTO colocateWordsDTO;
}
