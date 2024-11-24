package com.japanese.lessons.dtos;

import com.japanese.lessons.models.lesson.exercise.Guidance;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExplanationWithTableDTO {

    private Guidance guidance;
    private List<TableDTO> tableDTOList;
}
