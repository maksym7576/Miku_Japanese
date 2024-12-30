package com.japanese.lessons.service;

import com.japanese.lessons.dtos.LevelGuidanceDTO;
import com.japanese.lessons.models.LevelGuidance;
import com.japanese.lessons.models.first.ELessonLevels;
import com.japanese.lessons.repositories.ILevelGuidanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class LevelGuidanceService {

    @Autowired private ILevelGuidanceRepository iLevelGuidanceRepository;

    private List<LevelGuidance> getLevelGuidanceByLevel(ELessonLevels eLessonLevels) {
        return iLevelGuidanceRepository.findByELessonLevels(eLessonLevels);
    }

    private LevelGuidanceDTO formLevelGuidanceDTO(LevelGuidance levelGuidance, boolean isFinished) {
        return new LevelGuidanceDTO(levelGuidance.getId(), levelGuidance.getELevelGuidanceType().toString(), levelGuidance.getText(), isFinished);
    }

    private List<LevelGuidanceDTO> formLevelGuidanceDTOListAndSortByOrder(List<LevelGuidance> levelGuidanceList) {
        Collections.sort(levelGuidanceList, (a, b) -> Integer.compare(a.getOrderText(), b.getOrderText()));
        List<LevelGuidanceDTO> levelGuidanceDTOS = new ArrayList<>();
        for (LevelGuidance levelGuidance : levelGuidanceList) {
            //TODO add userProgress to compare is finished
            levelGuidanceDTOS.add(formLevelGuidanceDTO(levelGuidance, false));
        }
        return levelGuidanceDTOS;
    }

    public List<LevelGuidanceDTO> getLevelGuidanceDTOByLevel(ELessonLevels eLessonLevels) {
        List<LevelGuidance> levelGuidanceList = getLevelGuidanceByLevel(eLessonLevels);
        return new ArrayList<>(formLevelGuidanceDTOListAndSortByOrder(levelGuidanceList));
    }
}
