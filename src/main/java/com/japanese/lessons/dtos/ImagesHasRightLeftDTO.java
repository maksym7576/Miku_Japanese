package com.japanese.lessons.dtos;

import com.japanese.lessons.models.lesson.mangaExercise.Images;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ImagesHasRightLeftDTO {
    private Images imageRight;
    private Images imageLeft;
}
