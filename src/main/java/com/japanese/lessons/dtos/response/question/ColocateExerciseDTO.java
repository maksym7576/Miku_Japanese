package com.japanese.lessons.dtos.response.question;

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

    private Question question;
    private ColocateWordsDTO colocateWordsDTO;
}
