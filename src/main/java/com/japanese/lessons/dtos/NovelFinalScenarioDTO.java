package com.japanese.lessons.dtos;

import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NovelFinalScenarioDTO {

    private Integer needsAtLstCorrectAnswers;
    private List<StructuredDataForExercisesDTO> phraseList;
}
