package com.japanese.lessons.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.japanese.lessons.models.first.Lesson;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Block {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String topic;

    @Column
    private Integer position;

    @OneToMany(mappedBy = "block", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lesson> lessonList;

    @Column
    private Long examExerciseId;

    @ManyToOne
    @JoinColumn(name = "level_id")
    @JsonIgnore
    private Level level;
}

