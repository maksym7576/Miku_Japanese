package com.japanese.lessons.dtos;

import com.japanese.lessons.models.Audio;
import com.japanese.lessons.models.lesson.exercise.Question;
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
    private Audio audio;
}
