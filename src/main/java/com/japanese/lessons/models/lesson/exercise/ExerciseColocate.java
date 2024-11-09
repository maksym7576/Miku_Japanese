package com.japanese.lessons.models.lesson.exercise;

import com.japanese.lessons.models.Exercise;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class ExerciseColocate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String translationVersionColocated;

    @Column
    private String answer;

    @OneToMany(mappedBy = "exerciseColocate", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<WordsToColocate> wordsToColocateList;

    @ManyToOne
    @JoinColumn(name = "exercise")
    private Exercise exercise;
}
