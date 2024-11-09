package com.japanese.lessons.dtos;

import com.japanese.lessons.models.Image;
import com.japanese.lessons.models.lesson.mangaExercise.Text;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageMangaDTO {

    private Image image;
    private Text mangaPhotoDescription;
}