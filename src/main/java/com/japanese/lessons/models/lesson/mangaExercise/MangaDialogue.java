package com.japanese.lessons.models.lesson.mangaExercise;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class MangaDialogue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String dialogue;

    @Column
    private int turn;

    @ManyToOne(optional = true)
    @JoinColumn(name = "manga_id", nullable = true)
    @JsonBackReference
    private Manga manga;

    public boolean isComplete() {
        return turn > 0 && dialogue != null
               && manga != null;
    }

    public void copyNonNullProperties(MangaDialogue source) {
        if (source == null) {
            return;
        }
        if (source.getTurn() != 0) {
            this.turn = source.getTurn();
        }
        if (source.getDialogue() != null) {
            this.dialogue = source.getDialogue();
        }
        if (source.getManga() != null) {
            this.manga = source.getManga();
        }
    }
}
