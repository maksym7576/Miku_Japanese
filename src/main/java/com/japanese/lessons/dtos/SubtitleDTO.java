package com.japanese.lessons.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubtitleDTO {

    private Long id;
    private float startTime;
    private float endTime;
    private String text;
}
