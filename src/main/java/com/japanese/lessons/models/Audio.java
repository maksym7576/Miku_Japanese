package com.japanese.lessons.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Audio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "target_type", nullable = false)
    private ETargetType targetType;

    @Column(name = "target_id", nullable = false)
    private Long targetId;

    @Lob
    @Column(name = "audio_data", nullable = false, length = 2 * 1024 * 1024)
    private byte[] audioData;
}
