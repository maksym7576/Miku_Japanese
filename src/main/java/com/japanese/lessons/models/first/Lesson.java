package com.japanese.lessons.models.first;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.japanese.lessons.models.second.Exercise;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int position;

    @Enumerated(EnumType.STRING)
    private ELessonLevels level;

    @Column(nullable = false)
    private String videoPath;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Exercise> exercises;

    public void validateCompletion() {
        if (position < 0) {
            throw new IllegalArgumentException("Position cannot be less than 0.");
        }
        if (level == null) {
            throw new IllegalArgumentException("Level cannot be null.");
        }
//        if (videoPath == null) {
//            throw new IllegalArgumentException("Video path cannot be null.");
//        }
    }

    public void copyNonNullProperties(Lesson source) {
        if (source == null) {
            return;
        }
        if (source.getPosition() > 0) {
            this.position = source.getPosition();
        }
        if (source.getLevel() != null) {
            this.level = source.getLevel();
        }
        if (source.getVideoPath() != null) {
            this.videoPath = source.getVideoPath();
        }
    }
}
