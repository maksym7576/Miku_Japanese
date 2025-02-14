package com.japanese.lessons.dtos;

import com.japanese.lessons.dtos.response.models.PhraseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NovelTrueOrFalseLinePhrase {

    private Boolean isMainLine;
    private List<StructuredDataForExercisesDTO> phraseList;
}
