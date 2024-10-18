package com.japanese.lessons.models.LessonMicroservice.manga;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.japanese.lessons.models.LessonMicroservice.test.Question;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Manga_dialogs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String character_name;

    @Column
    private String content;

    @Column
    private String translation;

    @Column
    private int sequence_order;

    @ManyToOne(optional = true)
    @JoinColumn(name = "manga_id")
    @JsonBackReference
    private Manga manga;

    @ManyToOne(optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;
}
