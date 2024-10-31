package com.japanese.lessons.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MangaContentDTO {
    @JsonBackReference
    private int queue;
    private String type;
    private Object content;

    public MangaContentDTO(int queue, String type, Object content) {
        this.queue = queue;
        this.type = type;
        this.content = content;
    }
}
