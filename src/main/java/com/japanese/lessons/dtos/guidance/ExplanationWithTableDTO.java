package com.japanese.lessons.dtos.guidance;


import com.japanese.lessons.models.fourth.Guidance;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExplanationWithTableDTO {

    private Guidance guidance;
    private List<TableDTO> tableDTOList;
}
