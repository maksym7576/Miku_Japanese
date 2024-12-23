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
public class ColocateWordsWithListDTO {

    private Long textId;
    private String correctKanji;
    private String correctHiraganaKatakana;
    private String correctRomanji;
    private String translation;
    private List<WordDTO> wordsList;
}
