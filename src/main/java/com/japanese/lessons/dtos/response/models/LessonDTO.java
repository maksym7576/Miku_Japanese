package com.japanese.lessons.dtos.response.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LessonDTO {

    private Long id;
    private Integer position;
    private Integer box;
    private String level;
    private Boolean isFinished;
}
