package com.japanese.lessons.models.lesson.mangaExercise;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class MangaDialogue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @JsonBackReference
    private Integer queue;

    @Column
    private String dialogue_hiragana_katakana_kanji;

    @Column
    private String dialogue_hiragana_katakana;

    @Column
    private String dialogue_romanji;

    @Column
    private String translation;

    @ManyToOne(optional = true)
    @JoinColumn(name = "manga_id", nullable = true)
    @JsonBackReference
    private Manga manga;

    @JsonIgnore
    public boolean isComplete() {
        return queue > 0 && dialogue_hiragana_katakana_kanji != null && manga != null;
    }

    public void validateCompletion() {
        if (queue <= 0) {
            throw new IllegalArgumentException("Turn must be greater than 0: " + id);
        }

        if (dialogue_hiragana_katakana_kanji == null || dialogue_hiragana_katakana_kanji.trim().isEmpty()) {
            throw new IllegalArgumentException("Dialogue (Hiragana/Katakana/Kanji) cannot be null or empty: " + id);
        }

        if (translation == null) {
            throw new IllegalArgumentException("Translation cannot be null: " + id);
        }

        if (dialogue_hiragana_katakana == null) {
            throw new IllegalArgumentException("Dialogue hiragana katakana cannot be null: " + id);
        }

        if (dialogue_romanji == null) {
            throw new IllegalArgumentException("Dialogue romanji cannot be null: " + id);
        }

        if (manga == null) {
            throw new IllegalArgumentException("Manga cannot be null: " + id);
        }
    }

    public void copyNonNullProperties(MangaDialogue source) {
        if (source == null) {
            return;
        }
        if (source.getQueue() != 0) {
            this.queue = source.getQueue();
        }
        if (source.getDialogue_hiragana_katakana_kanji() != null) {
            this.dialogue_hiragana_katakana_kanji = source.getDialogue_hiragana_katakana_kanji();
        }
        if (source.getDialogue_hiragana_katakana() != null) {
            this.dialogue_hiragana_katakana = source.getDialogue_hiragana_katakana();
        }
        if (source.getDialogue_romanji() != null) {
            this.dialogue_romanji = source.getDialogue_romanji();
        }
        if (source.getTranslation() != null) {
            this.translation = source.getTranslation();
        }
        if (source.getManga() != null) {
            this.manga = source.getManga();
        }
    }
}
