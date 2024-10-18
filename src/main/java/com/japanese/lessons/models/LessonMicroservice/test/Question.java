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

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Quiz_answers> quizAnswers;

    @ManyToOne(optional = false)
    @JoinColumn(name = "manga_id", nullable = true)
    @JsonIgnore
    private Manga manga;
}
