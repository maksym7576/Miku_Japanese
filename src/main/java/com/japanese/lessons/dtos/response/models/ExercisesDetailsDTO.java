package com.japanese.lessons.dtos.response.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ExercisesDetailsDTO {
    private Long id;
    private String name;
    private String startDialogue;
}
