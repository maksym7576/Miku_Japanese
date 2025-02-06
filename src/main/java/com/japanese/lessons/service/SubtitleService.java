package com.japanese.lessons.service;

import com.japanese.lessons.dtos.SubtitleDTO;
import com.japanese.lessons.models.Subtitle;
import com.japanese.lessons.repositories.ISubtitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubtitleService {

    @Autowired private ISubtitleRepository iSubtitleRepository;

    private List<Subtitle> getAllSubtitleByVideoId(Long videoId) {
        return iSubtitleRepository.findByVideoId(videoId);
    }

    private SubtitleDTO formSubtitleDTO(Subtitle subtitle) {
        return new SubtitleDTO(subtitle.getId(), subtitle.getStartTime(), subtitle.getEndTime(), subtitle.getText());
    }

    private List<SubtitleDTO> formSubtitleDTOList(List<Subtitle> subtitleList) {
        List<SubtitleDTO> subtitleDTOList = new ArrayList<>();
        for (Subtitle subtitle : subtitleList) {
            subtitleDTOList.add(formSubtitleDTO(subtitle));
        }
        return subtitleDTOList;
    }

    public List<SubtitleDTO> getSubtitleListOrderedByVideoId(Long videoId) {
        List<Subtitle> subtitleList = getAllSubtitleByVideoId(videoId);
        return formSubtitleDTOList(subtitleList);
    }
}
