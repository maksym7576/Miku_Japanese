package com.japanese.lessons.models.first;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.japanese.lessons.models.Block;
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

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Exercise> exercises;

    @ManyToOne
    @JoinColumn(name = "block_id")
    @JsonIgnore
    private Block block;

    public void validateCompletion() {
        if (position < 0) {
            throw new IllegalArgumentException("Position cannot be less than 0.");
        }
    }
}
