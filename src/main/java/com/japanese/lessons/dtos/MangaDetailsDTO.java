package com.japanese.lessons.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MangaDetailsDTO {
    private Long id;
    private String name;
    private String startDialogue;
}
