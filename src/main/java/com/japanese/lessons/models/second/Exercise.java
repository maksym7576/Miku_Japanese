package com.japanese.lessons.models.second;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.japanese.lessons.models.first.Lesson;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "exercises", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"lesson_id", "eExerciseType"})
})
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String topic;

    @Column
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "e_exercise_type", nullable = false)
    private EExerciseType eExerciseType;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

}