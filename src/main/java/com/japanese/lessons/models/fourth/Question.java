package com.japanese.lessons.models.fourth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.japanese.lessons.dtos.QuestionJsonChooseTheContinuationDTO;
import com.japanese.lessons.dtos.QuestionJsonEnglishTextDTO;
import com.japanese.lessons.dtos.QuestionJsonIndexDTO;
import com.japanese.lessons.dtos.QuestionJsonStandardDTO;
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

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public List<QuestionJsonIndexDTO> getIndexFormat() {
        try {
            List<Map<String, Object>> answersList = objectMapper.readValue(idsAnswersJson, new TypeReference<>() {});
            return answersList.stream()
                    .map(item -> new QuestionJsonIndexDTO(
                            ((Number) item.get("wordId")).longValue(),
                            item.get("index") != null ? ((Number) item.get("index")).intValue() : 0))
                    .collect(Collectors.toList());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<QuestionJsonEnglishTextDTO> getEnglishFormat() {
        try {
            List<Map<String, Object>> answerList = objectMapper.readValue(idsAnswersJson, new TypeReference<List<Map<String, Object>>>() {});
            return answerList.stream()
                    .map(item -> new QuestionJsonEnglishTextDTO(
                            (String) item.get("question"),
                            (boolean) item.get("isCorrect")
                    ))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<QuestionJsonStandardDTO> getStandardFormat() {
        try {
            Map<String, Boolean> answersMap = objectMapper.readValue(idsAnswersJson, new TypeReference<>() {});
            return answersMap.entrySet().stream()
                    .map(entry -> new QuestionJsonStandardDTO(Long.parseLong(entry.getKey()), entry.getValue()))
                    .collect(Collectors.toList());
        } catch (JsonProcessingException e) {
            return Collections.emptyList();
        }
    }
    public List<QuestionJsonChooseTheContinuationDTO> getChooseTheContinuationFormat() {
        try {
            List<Map<String, Object>> answerList = objectMapper.readValue(idsAnswersJson, new TypeReference<List<Map<String, Object>>>() {});

            return answerList.stream()
                    .map(item -> new QuestionJsonChooseTheContinuationDTO(
                            item.get("miniQuestion") != null ? item.get("miniQuestion").toString() : null,
                            item.get("textId") != null ? ((Number) item.get("textId")).longValue() : null
                    ))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
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
