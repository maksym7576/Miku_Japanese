package com.japanese.lessons.models.LessonMicroservice.manga;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.japanese.lessons.models.Images;
import com.japanese.lessons.models.LessonMicroservice.Lesson;
import com.japanese.lessons.models.LessonMicroservice.test.Question;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Manga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "manga", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Images> images = new ArrayList<>();

    @OneToMany(mappedBy = "manga", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<MangaText> mangaTexts;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @OneToMany(mappedBy = "manga", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Question> questions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Images> getImages() {
        return images != null ? images : new ArrayList<>();  // Повертаємо порожній список, якщо images == null
    }

    public void setImages(List<Images> images) {
        this.images = images;
    }

    public Set<MangaText> getMangaTexts() {
        return mangaTexts != null ? mangaTexts : Set.of();  // Повертаємо порожню множину, якщо mangaTexts == null
    }

    public void setMangaTexts(Set<MangaText> mangaTexts) {
        this.mangaTexts = mangaTexts;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public Set<Question> getQuestions() {
        return questions != null ? questions : Set.of();  // Повертаємо порожню множину, якщо questions == null
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }
}
