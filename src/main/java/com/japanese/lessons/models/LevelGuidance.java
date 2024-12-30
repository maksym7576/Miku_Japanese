package com.japanese.lessons.models;

import com.japanese.lessons.models.first.ELessonLevels;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class LevelGuidance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer orderText;

    @Enumerated(EnumType.STRING)
    private ELevelGuidanceType eLevelGuidanceType;

    @Column
    private String text;

    @Column
    private Long lessonIdBelongs;

    @Enumerated(EnumType.STRING)
    private ELessonLevels eLessonLevels;
}
