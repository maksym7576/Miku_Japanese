package com.japanese.lessons.dtos.response.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RewardsDTO {

    private Long id;
    private String name;
    private String description;
    private String type;
}
