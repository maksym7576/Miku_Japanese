package com.japanese.lessons.models.lesson.exercise;

import com.japanese.lessons.models.EFileURLType;
import com.japanese.lessons.models.ETargetType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String question;

    @Column
    private Long correct_answer_id;

    @Column
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "target_type", nullable = true)
    private ETargetType targetType;

    @Column(name = "target_id", nullable = true)
    private Long targetId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_to_show")
    private EFileURLType eFileURLType;

    @Column(name = "type_to_show_id")
    private Long objectIdURL;

    public void validateCompletion() {

        if (question == null || question.trim().isEmpty()) {
            throw new IllegalArgumentException("Question cannot be null or empty" + id);
        }
    }

    public void copyNonNullProperties(Question source) {
        if (source == null) {
            return;
        }
        if (source.getQuestion() != null) {
            this.question = source.getQuestion();
        }
    }
}
