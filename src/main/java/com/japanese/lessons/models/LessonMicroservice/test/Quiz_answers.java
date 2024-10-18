package com.japanese.lessons.models.LessonMicroservice.test;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Quiz_answers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int sequence_order;

    @Column
    private String answer;

    @Column
    private String explanation;

    @Column
    private Boolean is_correct;

    @ManyToOne
    @JoinColumn(name = "question_id")
    @JsonIgnore
    private Question question;

}
