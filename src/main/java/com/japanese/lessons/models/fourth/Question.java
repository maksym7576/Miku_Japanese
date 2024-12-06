package com.japanese.lessons.models.fourth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;
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
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_to_show")
    private EMediaType eMediaType;

    @Column(name = "ids_answers_json", columnDefinition = "TEXT")
    private String idsAnswersJson;

    @Column(name = "ids_media")
    private String idsMediaString;

    public void setIdsAnswers(Map<Long, Boolean> answers) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            this.idsAnswersJson = objectMapper.writeValueAsString(answers);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            this.idsAnswersJson = "{}";
        }
    }
    public Map<Long, Boolean> getIdsAnswers() {
        if (idsAnswersJson == null || idsAnswersJson.isEmpty()) {
            return new HashMap<>();
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(idsAnswersJson,
                    objectMapper.getTypeFactory().constructMapType(Map.class, Long.class, Boolean.class));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
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
