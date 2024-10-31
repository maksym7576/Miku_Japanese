package com.japanese.lessons.dtos;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MangaDialogueDTO {
    private Long id;
    private String type;
    @JsonBackReference
    private Integer queue;
    private String dialogueHiraganaKatakanaKanji;
    private String dialogueHiraganaKatakana;
    private String dialogueRomanji;
    private String translation;
}