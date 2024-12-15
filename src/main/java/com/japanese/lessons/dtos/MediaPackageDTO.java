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
public class MediaPackageDTO {

    private String mediaType;
    private List<FileRecordsDTO> fileRecordsList;
}
