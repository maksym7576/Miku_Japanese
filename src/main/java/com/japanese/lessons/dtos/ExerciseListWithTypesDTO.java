package com.japanese.lessons.dtos;

import com.japanese.lessons.dtos.response.models.ExerciseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseListWithTypesDTO {
    private String type;
    private List<ExerciseDTO> exerciseList;
}
