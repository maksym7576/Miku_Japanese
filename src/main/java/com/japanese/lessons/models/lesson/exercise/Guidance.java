package com.japanese.lessons.models.lesson.exercise;
import com.japanese.lessons.models.EGuidanceType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Guidance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String topic;

    @Column
    private String description;

    @Enumerated(EnumType.STRING)
    @Column
    private EGuidanceType eGuidanceType;

}