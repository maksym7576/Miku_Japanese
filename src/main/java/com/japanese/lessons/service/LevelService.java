package com.japanese.lessons.service;

import com.japanese.lessons.dtos.BlockDTO;
import com.japanese.lessons.dtos.LevelDTO;
import com.japanese.lessons.dtos.LevelGuidanceDTO;
import com.japanese.lessons.models.Block;
import com.japanese.lessons.models.Level;
import com.japanese.lessons.repositories.ILevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LevelService {

    @Autowired private ILevelRepository iLevelRepository;
    @Autowired private LevelGuidanceService levelGuidanceService;
    @Autowired private BlockService blockService;

    public List<Level> getAllLevelsAndSort() {
        try {
            return iLevelRepository.findAll(Sort.by(Sort.Order.desc("level")));
        } catch (IllegalAccessError e) {
            throw new RuntimeException("An error has occurred: " + e.getMessage(), e);
        }
    }

    private LevelDTO formLevelsDTO(Level level, Long userId) {
        List<LevelGuidanceDTO> levelGuidanceDTOList = levelGuidanceService.getLevelGuidanceDTOByLevel(level.getLevel());
        List<Block> blockList = level.getBlockList();
        List<BlockDTO> blockDTOList = new ArrayList<>();
        for (Block block : blockList) {
            blockDTOList.add(blockService.formBlockDTOAndFormLessonDTOList(block, userId));
        }
        return new LevelDTO(level.getLevel().toString(), level.getId(), levelGuidanceDTOList, blockDTOList);
    }

    public List<LevelDTO> getAllLevelsAndFormAllLessonsWithUserId(Long userId) {
        List<Level> levelList = getAllLevelsAndSort();
        List<LevelDTO> levelDTOList = new ArrayList<>();
        for (Level level : levelList) {
            levelDTOList.add(formLevelsDTO(level, userId));
        }
        return levelDTOList;
    }
}
