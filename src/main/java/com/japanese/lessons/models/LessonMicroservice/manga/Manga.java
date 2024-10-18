package com.japanese.lessons.models.LessonMicroservice.manga;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.japanese.lessons.models.Images;
import com.japanese.lessons.models.LessonMicroservice.Lesson;
import com.japanese.lessons.models.LessonMicroservice.test.Question;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Manga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "manga", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Images> images = new ArrayList<>();

    @OneToMany(mappedBy = "manga", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Manga_dialogs> mangadialogs;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @OneToMany(mappedBy = "manga", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Question> Questions;
}
