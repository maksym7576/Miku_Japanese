package com.japanese.lessons.dtos;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class QuestionMangaDTO {
    private Long id;
    private String type;
    @JsonBackReference
    private Integer queue;
    private String question;
    private String correctAnswerOriginal;
    private String correctAnswerHiraganaOrKatakana;
    private String correctAnswerRomanized;
    private String translationCorrectAnswer;
    private List<AnswerMangaDTO> answerMangas;
}