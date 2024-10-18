package com.japanese.lessons.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.japanese.lessons.models.LessonMicroservice.manga.Manga;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Images {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(nullable = false, length = 16 * 1024 * 1024)
    private byte[] imageData;

    @Column
    private int turn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manga_id")
    @JsonIgnore
    private Manga manga;

    public Images() {
    }

    public Images(byte[] imageData, Manga manga, int turn) {
        this.imageData = imageData;
        this.manga = manga;
        this.turn = turn;
    }
    @JsonIgnore
    public boolean isImageEmpty() {
        return this.imageData == null || this.imageData.length == 0;
    }
}
