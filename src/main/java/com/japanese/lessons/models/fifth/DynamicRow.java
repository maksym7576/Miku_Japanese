package com.japanese.lessons.models.fifth;

import com.japanese.lessons.models.EDynamicRowsTypes;
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
//@Table(name = "\"dynamic_row\"")
public class DynamicRow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(100) COLLATE utf8mb4_general_ci")
    private String tableName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EDynamicRowsTypes rowsType;

    @Column(name = "ids_string", nullable = false)
    private String textIds;

    public void setIds(List<Long> ids) {
        this.textIds = ids.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }

    public List<Long> getIds() {
        if(textIds == null || textIds.isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.stream(textIds.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }
}
