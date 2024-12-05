package com.japanese.lessons.dtos.response.models;

import com.japanese.lessons.models.fourth.EMediaType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PhraseDTO {

    private Long id;
    private EMediaType mediaType;
}
