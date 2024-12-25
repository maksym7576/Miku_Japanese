package com.japanese.lessons.models.second;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.japanese.lessons.models.first.Lesson;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Table(name = "exercises", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"lesson_id", "eExerciseType"})
})
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String topic;

    @Column
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "e_exercise_type", nullable = false)
    private EExerciseType eExerciseType;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    @Column(name = "ids_rewards")
    private String rewardsIds;

    public void setIdsRewards(List<Long> ids) {
        this.rewardsIds = ids.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }

    public List<Long> getIdsRewards() {
        if (rewardsIds == null || rewardsIds.isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.stream(rewardsIds.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }

}