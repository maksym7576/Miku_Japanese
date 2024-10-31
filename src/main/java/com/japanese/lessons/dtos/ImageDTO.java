package com.japanese.lessons.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageDTO {
    private Long id;

    @JsonBackReference
    private Integer queue;

    private byte[] imageData;

    public ImageDTO(Long id, byte[] imageData) {
        this.id = id;
        this.imageData = imageData;
    }
}