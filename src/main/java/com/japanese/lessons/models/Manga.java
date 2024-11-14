package com.japanese.lessons.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.japanese.lessons.models.lesson.Lesson;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Manga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String startDialog;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;


    public void validateCompletion() {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null.");
        }
        if (startDialog == null) {
            throw new IllegalArgumentException("Start dialog cannot be null.");
        }
        if (lesson == null) {
            throw new IllegalArgumentException("Lesson cannot be null.");
        }
    }



    public void copyNonNullProperties(Manga source) {
        if (source == null) {
            return;
        }
        if (source.getName() != null) {
            this.name = source.getName();
        }
        if (source.getStartDialog() != null) {
            this.startDialog = source.getStartDialog();
        }
    }


}