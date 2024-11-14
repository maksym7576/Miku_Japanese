package com.japanese.lessons.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(nullable = false, length = 16 * 1024 * 1024)
    private byte[] imageData;

    @Enumerated(EnumType.STRING)
    @Column(name = "target_type", nullable = true)
    private ETargetType targetType;

    @Column(name = "target_id", nullable = true)
    private Long targetId;

    public Image() {
    }

    public Image(byte[] imageData, int queue) {
        this.imageData = imageData;
    }
}
