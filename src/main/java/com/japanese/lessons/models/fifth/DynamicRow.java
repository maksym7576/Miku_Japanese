package com.japanese.lessons.models.fifth;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@NoArgsConstructor
public class DynamicRow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tableName;

    @Column(name = "guidance_id", nullable = true)
    private Long guidanceId;

    @Column(name = "ids_string")
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
