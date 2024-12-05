package com.japanese.lessons.dtos;

import com.japanese.lessons.dtos.response.models.PhraseDTO;
import com.japanese.lessons.dtos.response.models.TextDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PhraseDTOWithSentence {

    private PhraseDTO phrase;
    private TextDTO textDTO;
}
