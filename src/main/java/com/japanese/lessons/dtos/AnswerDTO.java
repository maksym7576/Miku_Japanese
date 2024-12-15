package com.japanese.lessons.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDTO {

    private Long id;
    private String kanji_word;
    private String hiragana_or_katakana;
    private String romanji_word;
    private String translation;
    private boolean isCorrect;
}
