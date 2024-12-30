package com.japanese.lessons.dtos;

import com.japanese.lessons.dtos.response.models.LessonDTO;
import com.japanese.lessons.models.first.Lesson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BlockDTO {

    private Long id;
    private String topic;
    private List<LessonDTO> lessonList;
}
