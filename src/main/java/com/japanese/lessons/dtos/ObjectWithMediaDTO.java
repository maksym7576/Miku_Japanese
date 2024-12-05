package com.japanese.lessons.dtos;

import com.japanese.lessons.dtos.response.models.FileRecordsDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ObjectWithMediaDTO {

    private Object object;
    private List<FileRecordsDTO> fileRecordsList;
}
