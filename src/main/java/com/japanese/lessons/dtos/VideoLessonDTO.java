package com.japanese.lessons.dtos;

import com.japanese.lessons.dtos.response.models.FileRecordsDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.opencv.video.Video;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VideoLessonDTO {

    private Long exerciseId;
    private FileRecordsDTO fileRecords;
    private List<SubtitleDTO> subtitleList;
}
