package com.japanese.lessons.models.lesson.exercise;
import com.japanese.lessons.models.Exercise;
import jakarta.persistence.*;

@Entity
public class ExerciseSelect {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String question;

    @Column(nullable = false)
    private String answer;

    @Column
    private String version1;

    @Column
    private String version2;

    @Column
    private String version3;

    @Column
    private String version4;

    @ManyToOne
    @JoinColumn(name = "exercise")
    private Exercise exercise;
}