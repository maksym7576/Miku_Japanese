package com.japanese.lessons.models.fourth;
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
public class Guidance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(100) COLLATE utf8mb4_general_ci")
    private String topic;

    @Column(columnDefinition = "VARCHAR(500) COLLATE utf8mb4_general_ci")
    private String description;

    @Column(name = "ids_dynamic_row")
    private String dynamicRowIds;

    public void setIds(List<Long> ids) {
        this.dynamicRowIds = ids.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }

    public List<Long> getIds() {
        if(dynamicRowIds == null || dynamicRowIds.isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.stream(dynamicRowIds.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }

}