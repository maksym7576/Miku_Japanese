package com.japanese.lessons.models.lesson.exercise;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String topic;

    @Column
    private String description;

    @OneToMany(mappedBy = "exercise", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Fact> facts;

    @OneToMany(mappedBy = "exercise", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ExerciseSelect> exerciseSelects;

    @OneToMany(mappedBy = "exercise", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ExerciseColocate> exerciseColocateList;
}