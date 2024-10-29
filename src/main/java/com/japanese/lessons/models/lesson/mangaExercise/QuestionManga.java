package com.japanese.lessons.models.lesson.mangaExercise;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class QuestionManga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String type = "Question";

    @Column
    private int queue;

    @Column
    private String question;

    @Column
    private String correct_answer_original;

    @Column
    private String correct_answer_hiragana_or_katakana;

    @Column
    private String correct_answer_romanized;

    @Column
    private String translation_correct_answer;

    @Column

    @OneToMany(mappedBy = "questionManga", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnswerManga> answerMangas;

    @ManyToOne
    @JoinColumn(name = "manga_id", nullable = true)
    @JsonBackReference
    private Manga manga;

    public void validateCompletion() {

        if (queue <= 0) {
            throw new IllegalArgumentException("Queue must be greater than 0 " + id);
        }

        if (question == null || question.trim().isEmpty()) {
            throw new IllegalArgumentException("Question cannot be null or empty" + id);
        }

        if (correct_answer_original == null) {
            throw new IllegalArgumentException("Correct original answer cannot be null" + id);
        }
        if (correct_answer_hiragana_or_katakana == null) {
            throw new IllegalArgumentException("Correct answer hiragana or katakana cannot be null" + id);
        }
        if (correct_answer_romanized == null) {
            throw new IllegalArgumentException("Correct answer romanized cannot be null" + id);
        }
        if (translation_correct_answer == null) {
            throw new IllegalArgumentException("Translation correct answer cannot be null" + id);
        }

        if (manga == null) {
            throw new IllegalArgumentException("Manga cannot be null" + id);
        }
    }

    public void copyNonNullProperties(QuestionManga source) {
        if (source == null) {
            return;
        }
        if (source.getQueue() != 0) {
            this.queue = source.getQueue();
        }
        if (source.getQuestion() != null) {
            this.question = source.getQuestion();
        }
        if (source.getCorrect_answer_original() != null) {
            this.correct_answer_original = source.getCorrect_answer_original();
        }
        if (source.getCorrect_answer_hiragana_or_katakana() != null) {
            this.correct_answer_hiragana_or_katakana = source.getCorrect_answer_hiragana_or_katakana();
        }
        if (source.getCorrect_answer_romanized() != null) {
            this.correct_answer_romanized = source.getCorrect_answer_romanized();
        }
        if (source.getTranslation_correct_answer() != null) {
            this.translation_correct_answer = source.getTranslation_correct_answer();
        }
        if (source.getAnswerMangas() != null) {
            this.answerMangas = source.getAnswerMangas();
        }
        if(source.getManga() != null) {
            this.manga = source.getManga();
        }
    }
}
