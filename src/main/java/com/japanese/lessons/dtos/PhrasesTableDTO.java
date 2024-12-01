package com.japanese.lessons.dtos;

import com.japanese.lessons.models.DynamicRow;
import com.japanese.lessons.models.lesson.exercise.Guidance;
import com.japanese.lessons.models.lesson.mangaExercise.Text;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PhrasesTableDTO {

    private DynamicRow dynamicRow;
    private List<Text> textList;
}
