package com.japanese.lessons.models.lesson.exercise;

import com.japanese.lessons.models.Exercise;
import com.japanese.lessons.models.Image;
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


}