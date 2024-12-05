package com.japanese.lessons.dtos.response.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GuidanceDTO {

    private Long id;
    private String topic;
    private String description;
}
