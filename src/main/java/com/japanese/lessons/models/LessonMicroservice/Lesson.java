package com.japanese.lessons.models.LessonMicroservice;

import com.japanese.lessons.models.LessonMicroservice.manga.Manga;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int position;

    @Column
    private String level;

    @Column(nullable = false)
    private String videoPath;

    @OneToOne(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
    private Manga manga;
}
