package com.japanese.lessons.models.lesson.mangaExercise;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.japanese.lessons.models.ETargetType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @JsonBackReference
    private Integer queue;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(nullable = false, length = 16 * 1024 * 1024)
    private byte[] imageData;

    @Column
    private String position;

    @Enumerated(EnumType.STRING)
    @Column(name = "target_type", nullable = false)
    private ETargetType targetType;

    @Column(name = "target_id", nullable = false)
    private Long targetId;

    public Image() {
    }

    public Image(byte[] imageData, int queue) {
        this.imageData = imageData;
        this.queue = queue;
    }
}
