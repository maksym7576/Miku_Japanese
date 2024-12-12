package com.japanese.lessons.dtos;

import com.japanese.lessons.dtos.response.question.ColocateWordsDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ColocateWordsWithCorrectWordsListDTO {

    private ColocateWordsDTO colocateWords;
    private String[] correctWordsKatakana;
    private String[] correctsWordsHiraganaKanji;
    private String[] correctWordsRomanji;
}
