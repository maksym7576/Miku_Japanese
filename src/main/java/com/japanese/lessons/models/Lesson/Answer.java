package com.japanese.lessons.models.Lesson;

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
    private int turn;

    @Column
    private String answer;

    @Column
    private Boolean isCorrect;

    @ManyToOne
    @JoinColumn(name = "question_id")
    @JsonBackReference
    private Question question;

    public boolean isComplete() {
        return turn > 0 && answer != null && !answer.isEmpty()
                && isCorrect != null && question != null;
    }

    public void copyNonNullProperties(Answer source) {
        if (source == null) {
            return;
        }
        if (source.getTurn() != 0) {
            this.turn = source.getTurn();
        }
        if (source.getAnswer() != null) {
            this.answer = source.getAnswer();
        }
        if (source.getIsCorrect() != null) {
            this.isCorrect = source.getIsCorrect();
        }
        if (source.getQuestion() != null) {
            this.question = source.getQuestion();
        }
    }
}
