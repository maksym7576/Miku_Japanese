package com.japanese.lessons.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LevelGuidanceDTO {

    private Long id;
    private String type;
    private String text;
    private Boolean isCompleted;
}
