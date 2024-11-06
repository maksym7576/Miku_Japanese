package com.japanese.lessons.models.lesson.mangaExercise;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.japanese.lessons.models.ETargetType;
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
public class MangaPhotoDescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String dialogue_hiragana_katakana_kanji;

    @Column
    private String dialogue_hiragana_katakana;

    @Column
    private String dialogue_romanji;

    @Column
    private String translation;

    @Enumerated(EnumType.STRING)
    @Column(name = "target_type", nullable = false)
    private ETargetType targetType;

    @Column(name = "target_id", nullable = false)
    private Long targetId;
}
