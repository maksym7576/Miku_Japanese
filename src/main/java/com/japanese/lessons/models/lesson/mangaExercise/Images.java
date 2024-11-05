package com.japanese.lessons.models.lesson.mangaExercise;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @Column
    @JsonBackReference
    private Integer queue;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(nullable = false, length = 16 * 1024 * 1024)
    private byte[] imageData;

    @Column
    private String position;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "manga_id", nullable = true)
    @JsonIgnore
    private Manga manga;

    @OneToOne(mappedBy = "images", cascade = CascadeType.ALL, orphanRemoval = true)
    private MangaPhotoDescription mangaPhotoDescription;

    public Images() {
    }

    public Images(byte[] imageData, Manga manga, int queue) {
        this.imageData = imageData;
        this.manga = manga;
        this.queue = queue;
    }
}
