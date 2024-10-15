package com.japanese.lessons.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.japanese.lessons.models.LessonMicroservice.manga.Manga;
import jakarta.persistence.*;

@Entity
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

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "manga_id", nullable = true)
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public Manga getManga() {
        return manga;
    }

    public void setManga(Manga manga) {
        this.manga = manga;
    }
}
