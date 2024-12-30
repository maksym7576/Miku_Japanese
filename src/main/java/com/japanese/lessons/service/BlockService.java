package com.japanese.lessons.service;

import com.japanese.lessons.dtos.BlockDTO;
import com.japanese.lessons.dtos.response.models.LessonDTO;
import com.japanese.lessons.models.Block;
import com.japanese.lessons.models.first.Lesson;
import com.japanese.lessons.repositories.IBlockRepository;
import com.japanese.lessons.service.Lesson.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlockService {

    @Autowired private IBlockRepository iBlockRepository;
    @Autowired private LessonService lessonService;
    private BlockDTO formBlockDTO(Block block, List<LessonDTO> lessonDTOList) {
        return new BlockDTO(block.getId(), block.getTopic(), lessonDTOList);
    }

    public BlockDTO formBlockDTOAndFormLessonDTOList(Block block, Long userId) {
        List<LessonDTO> lessonDTOList = lessonService.markIsFinishedInLessonList(userId, block.getLessonList());
        return formBlockDTO(block, lessonDTOList);
    }
}
