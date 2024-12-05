package com.japanese.lessons.models.fourth;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Phrase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long textId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_to_show")
    private EMediaType eMediaType;

    @Column(name = "ids_media")
    private String idsString;

    public void setIds(List<Long> ids) {
        this.idsString = ids.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }

    public List<Long> getIds() {
        if(idsString == null || idsString.isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.stream(idsString.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }
}
