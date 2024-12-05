package com.japanese.lessons.dtos.guidance;

import com.japanese.lessons.dtos.response.models.DynamicRowDTO;
import com.japanese.lessons.models.fifth.DynamicRow;
import com.japanese.lessons.models.sixsth.Text;
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

    private DynamicRowDTO dynamicRow;
    private List<Text> textList;
}
