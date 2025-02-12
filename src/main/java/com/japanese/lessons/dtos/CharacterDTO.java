package com.japanese.lessons.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CharacterDTO {

    private String characterName;
    private String characterHistory;
    private Integer characterLevel;
    private Integer characterMood;
}
