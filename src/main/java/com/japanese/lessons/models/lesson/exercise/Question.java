package com.japanese.lessons.models.lesson.exercise;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.japanese.lessons.models.ETargetType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @JsonBackReference
    private int queue;

    @Column
    private String question;

    @Column
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Answer> answerMangas;

    @Enumerated(EnumType.STRING)
    @Column(name = "target_type", nullable = false)
    private ETargetType targetType;

    @Column(name = "target_id", nullable = false)
    private Long targetId;

    public void validateCompletion() {

        if (queue <= 0) {
            throw new IllegalArgumentException("Queue must be greater than 0 " + id);
        }

        if (question == null || question.trim().isEmpty()) {
            throw new IllegalArgumentException("Question cannot be null or empty" + id);
        }
    }

    public void copyNonNullProperties(Question source) {
        if (source == null) {
            return;
        }
        if (source.getQueue() != 0) {
            this.queue = source.getQueue();
        }
        if (source.getQuestion() != null) {
            this.question = source.getQuestion();
        }
    }
}
