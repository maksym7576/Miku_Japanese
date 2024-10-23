package com.japanese.lessons.models.lesson.exercise;

import jakarta.persistence.*;

@Entity
public class WordsToColocate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String word;

    @ManyToOne
    @JoinColumn(name = "exercise_colocate_id")
    private ExerciseColocate exerciseColocate;
}

