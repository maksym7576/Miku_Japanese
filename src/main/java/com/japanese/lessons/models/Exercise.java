package com.japanese.lessons.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.japanese.lessons.dtos.TypeObjectDTO;
import com.japanese.lessons.models.lesson.Lesson;
import com.japanese.lessons.models.lesson.exercise.ExerciseColocate;
import com.japanese.lessons.models.lesson.exercise.ExerciseSelect;
import com.japanese.lessons.models.lesson.exercise.Fact;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @ElementCollection
    @CollectionTable(name = "id_and_type_list", joinColumns = @JoinColumn(name = "data_entety_id"))
    private List<TypeObjectDTO> objectList;
}