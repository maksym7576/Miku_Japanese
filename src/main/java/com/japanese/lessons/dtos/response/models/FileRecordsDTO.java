package com.japanese.lessons.dtos.response.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileRecordsDTO {

    private Long id;
    private String url;
    private String type;
}
