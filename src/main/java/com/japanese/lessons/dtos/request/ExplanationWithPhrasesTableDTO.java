package com.japanese.lessons.dtos.request;


import com.japanese.lessons.dtos.PhrasesTableDTO;
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
public class ExplanationWithPhrasesTableDTO {

    private Guidance guidance;
    private List<PhrasesTableDTO> phrasesTableDTOList;
}
