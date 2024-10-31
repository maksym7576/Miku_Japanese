package com.japanese.lessons.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AnswerMangaDTO {
    private Long id;
    private String answerOriginal;
    private String answerHiraganaKatakana;
    private String answerRomanji;
    private boolean complete;
}