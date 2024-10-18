package com.japanese.lessons.models.LessonMicroservice.test;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.japanese.lessons.models.LessonMicroservice.manga.Manga;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
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

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Quiz_answers> quizAnswers;

    @ManyToOne(optional = true)
    @JoinColumn(name = "manga_id")
    @JsonIgnore
    private Manga manga;

    public Question copyTemplate() {
        Question copy = new Question();
        copy.setTurn(this.turn);
        copy.setQuestion(this.question);
        copy.setCorrectAnswer(this.correctAnswer);
        copy.setQuizAnswers(this.quizAnswers);
        copy.setManga(this.manga);
        return copy;
    }

    public boolean isAllFieldsFilled() {
        return question != null && !question.isEmpty() &&
                correctAnswer != null && !correctAnswer.isEmpty() &&
                turn >= 0 &&
                manga != null;
    }
}
