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
public class AnswerManga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int turn;

    @Column
    private String answerOriginal;

    @Column
    private String answer_hiragana_katakana;

    @Column
    private String answer_romanji;

    @ManyToOne
    @JoinColumn(name = "question_id")
    @JsonBackReference
    private QuestionManga questionManga;

    public boolean isComplete() {
        return turn > 0 && answerOriginal != null && !answerOriginal.isEmpty()
                 && questionManga != null;
    }

    public void copyNonNullProperties(AnswerManga source) {
        if (source == null) {
            return;
        }
        if (source.getTurn() != 0) {
            this.turn = source.getTurn();
        }
        if (source.getAnswerOriginal() != null) {
            this.answerOriginal = source.getAnswerOriginal();
        }
        if (source.getAnswer_hiragana_katakana() != null) {
            this.answer_hiragana_katakana = source.getAnswer_hiragana_katakana();
        }
        if (source.getAnswer_romanji() != null) {
            this.answer_romanji = source.getAnswer_romanji();
        }
        if (source.getQuestionManga() != null) {
            this.questionManga = source.getQuestionManga();
        }
    }
}
