package com.japanese.lessons.models.Lesson;

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
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int turn;

    @Column
    private String question;

    @Column
    private String correctAnswer;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Answer> answers;

    @ManyToOne
    @JoinColumn(name = "manga_id", nullable = true)
    @JsonBackReference
    private Manga manga;

    public void validateCompletion() {
        if (turn <= 0) {
            throw new IllegalArgumentException("Turn must be greater than 0 " + id);
        }

        if (question == null || question.trim().isEmpty()) {
            throw new IllegalArgumentException("Question cannot be null or empty" + id);
        }

        if (correctAnswer == null) {
            throw new IllegalArgumentException("Correct answer cannot be null" + id);
        }

        if (manga == null) {
            throw new IllegalArgumentException("Manga cannot be null" + id);
        }
    }

    public void copyNonNullProperties(Question source) {
        if (source == null) {
            return;
        }
        if (source.getTurn() != 0) {
            this.turn = source.getTurn();
        }
        if (source.getQuestion() != null) {
            this.question = source.getQuestion();
        }
        if (source.getCorrectAnswer() != null) {
            this.correctAnswer = source.getCorrectAnswer();
        }
        if (source.getAnswers() != null) {
            this.answers = source.getAnswers();
        }
        if(source.getManga() != null) {
            this.manga = source.getManga();
        }
    }
}
