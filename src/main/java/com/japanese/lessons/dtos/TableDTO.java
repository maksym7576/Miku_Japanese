package com.japanese.lessons.dtos;

import com.japanese.lessons.models.DynamicRow;
import com.japanese.lessons.models.Vocabulary;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TableDTO {

    private DynamicRow dynamicRow;
    private List<Vocabulary> vocabularyList;
}
