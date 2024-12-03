package com.japanese.lessons.dtos;

import com.japanese.lessons.models.FileRecords;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ObjectWithVideoDTO {

    private Object object;
    private FileRecords fileRecords;
}
