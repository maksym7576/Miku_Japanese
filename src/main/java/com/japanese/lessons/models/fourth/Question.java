package com.japanese.lessons.models.fourth;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String question;

    @Column
    private Long correct_answer_id;

    @Column
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_to_show")
    private EMediaType eMediaType;

    @Column(name = "ids_answers")
    private String idsAnswersString;

    @Column(name = "ids_media")
    private String idsMediaString;

    public void setIdsAnswers(List<Long> ids) {
        this.idsAnswersString = ids.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }

    public List<Long> getIdsAnswers() {
        if (idsAnswersString == null || idsAnswersString.isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.stream(idsAnswersString.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }

    public void setIdsMedia(List<Long> ids) {
        this.idsMediaString = ids.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }

    public List<Long> getIdsMedia() {
        if (idsMediaString == null || idsMediaString.isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.stream(idsMediaString.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }
}
