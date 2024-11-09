package com.japanese.lessons.models.lesson.mangaExercise;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String answer_hiragana_katakana_kanji;

    @Column
    private String answer_hiragana_katakana;

    @Column
    private String answer_romanji;

    @ManyToOne
    @JoinColumn(name = "question_id")
    @JsonBackReference
    private Question question;

    public boolean isComplete() {
        return answer_hiragana_katakana_kanji != null && !answer_hiragana_katakana_kanji.isEmpty()
                 && question != null;
    }

    public void copyNonNullProperties(Answer source) {
        if (source == null) {
            return;
        }
        if (source.getAnswer_hiragana_katakana_kanji() != null) {
            this.answer_hiragana_katakana_kanji = source.getAnswer_hiragana_katakana_kanji();
        }
        if (source.getAnswer_hiragana_katakana() != null) {
            this.answer_hiragana_katakana = source.getAnswer_hiragana_katakana();
        }
        if (source.getAnswer_romanji() != null) {
            this.answer_romanji = source.getAnswer_romanji();
        }
        if (source.getQuestion() != null) {
            this.question = source.getQuestion();
        }
    }

    public void validateCompletion() {
        if (answer_hiragana_katakana_kanji == null || answer_hiragana_katakana_kanji.trim().isEmpty()) {
            throw new IllegalArgumentException("Answer original cannot be null or empty.");
        }
        if (question == null) {
            throw new IllegalArgumentException("Question manga cannot be null.");
        }
        if (answer_hiragana_katakana == null || answer_hiragana_katakana.trim().isEmpty()) {
            throw new IllegalArgumentException("Hiragana/Katakana answer cannot be null or empty.");
        }
        if (answer_romanji == null || answer_romanji.trim().isEmpty()) {
            throw new IllegalArgumentException("Romanji answer cannot be null or empty.");
        }
    }
}
