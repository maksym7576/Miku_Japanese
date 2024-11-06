package com.japanese.lessons.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ImagesHasRightLeftDTO {
    private ImageMangaDTO imageRight;
    private ImageMangaDTO imageLeft;
}
