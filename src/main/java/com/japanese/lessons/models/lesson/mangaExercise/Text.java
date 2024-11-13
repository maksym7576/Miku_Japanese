package com.japanese.lessons.models.lesson.mangaExercise;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.japanese.lessons.models.ETargetType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Text {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @JsonBackReference
    private Integer queue;

    @Column
    private String kanji;

    @Column
    private String hiragana_or_katakana;

    @Column
    private String romanji;

    @Column
    private String translation;

    @Enumerated(EnumType.STRING)
    @Column(name = "target_type", nullable = false)
    private ETargetType targetType;

    @Column(name = "target_id", nullable = false)
    private Long targetId;

    @JsonIgnore
    public boolean isComplete() {
        return queue > 0 && kanji != null;
    }

    public void validateCompletion() {
        if (queue <= 0) {
            throw new IllegalArgumentException("Turn must be greater than 0: " + id);
        }

        if (kanji == null || kanji.trim().isEmpty()) {
            throw new IllegalArgumentException("Dialogue (Hiragana/Katakana/Kanji) cannot be null or empty: " + id);
        }

        if (translation == null) {
            throw new IllegalArgumentException("Translation cannot be null: " + id);
        }

        if (hiragana_or_katakana == null) {
            throw new IllegalArgumentException("Dialogue hiragana katakana cannot be null: " + id);
        }

        if (romanji == null) {
            throw new IllegalArgumentException("Dialogue romanji cannot be null: " + id);
        }
    }

    public void copyNonNullProperties(Text source) {
        if (source == null) {
            return;
        }
        if (source.getQueue() != 0) {
            this.queue = source.getQueue();
        }
        if (source.getKanji() != null) {
            this.kanji = source.getKanji();
        }
        if (source.getHiragana_or_katakana() != null) {
            this.hiragana_or_katakana = source.getHiragana_or_katakana();
        }
        if (source.getRomanji() != null) {
            this.romanji = source.getRomanji();
        }
        if (source.getTranslation() != null) {
           this.translation = source.getTranslation();
        }
    }
}
