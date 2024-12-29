package com.japanese.lessons.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LessonDetailsDTO {

    private List<ExerciseListWithTypesDTO> exercisesListWithType;
    private List<String> explainList;
}
