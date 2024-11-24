package com.japanese.lessons.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Vocabulary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String kanji_word;

    @Column
    private String hiragana_or_katakana;

    @Column
    private String romanji_word;

    @Column
    private String translation;

    @Enumerated(EnumType.STRING)
    @Column
    private EPartsOfSpeech ePartsOfSpeech;

    @Enumerated(EnumType.STRING)
    @Column
    private EWordSubtype eWordSubtype;

}
