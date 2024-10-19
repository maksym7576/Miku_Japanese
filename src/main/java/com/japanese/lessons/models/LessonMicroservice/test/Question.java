package com.japanese.lessons.models.LessonMicroservice.test;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.japanese.lessons.models.LessonMicroservice.manga.Manga;
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

    public boolean isComplete() {
        return turn > 0 && question != null && !answers.isEmpty()
                && correctAnswer != null && manga != null;
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
