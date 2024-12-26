package com.japanese.lessons.models.User;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long objectFinishedId;

    @Column(name = "lesson_id")
    private Long lessonId;

    @Enumerated(EnumType.STRING)
    @Column(name = "e_finished_types")
    private EFinishedTypes eFinishedTypes;

    @Column(name = "user_id")
    private Long userId;
}
