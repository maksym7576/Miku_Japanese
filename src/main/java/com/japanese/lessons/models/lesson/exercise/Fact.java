package com.japanese.lessons.models.lesson.exercise;

import com.japanese.lessons.models.Images;
import jakarta.persistence.*;

@Entity
public class Fact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String topic;

    @Column
    private String description;

    @Column
    private int exerciseTurn;

    @ManyToOne
    @JoinColumn(name = "exercise")
    private Exercise exercise;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Images image;
}