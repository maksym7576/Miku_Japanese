package com.japanese.lessons.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LevelDTO {
    private String Level;
    private Long id;
    private List<LevelGuidanceDTO> levelGuidanceList;
    private List<BlockDTO> blockList;
}
