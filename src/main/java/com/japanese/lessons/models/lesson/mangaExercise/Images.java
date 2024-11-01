package com.japanese.lessons.models.lesson.mangaExercise;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonBackReference
    private Integer queue;

    @Column
    private String position;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "manga_id", nullable = true)
    @JsonIgnore
    private Manga manga;

    public Images() {
    }

    public Images(byte[] imageData, Manga manga, int queue) {
        this.imageData = imageData;
        this.manga = manga;
        this.queue = queue;
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

    public int getQueue() {
        return queue;
    }

    public void setQueue(int queue) {
        this.queue = queue;
    }

    public Manga getManga() {
        return manga;
    }

    public void setManga(Manga manga) {
        this.manga = manga;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
