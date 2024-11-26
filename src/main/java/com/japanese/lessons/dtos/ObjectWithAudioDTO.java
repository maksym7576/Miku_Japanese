package com.japanese.lessons.dtos;

import com.japanese.lessons.models.Audio;
import com.japanese.lessons.models.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ObjectWithAudioDTO {

    private Object object;
    private Audio audio;
    private Image image;
}
