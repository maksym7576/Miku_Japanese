package com.japanese.lessons.service;

import com.japanese.lessons.dtos.ExplanationWithTableDTO;
import com.japanese.lessons.dtos.TableDTO;
import com.japanese.lessons.models.DynamicRow;
import com.japanese.lessons.models.Vocabulary;
import com.japanese.lessons.models.lesson.exercise.Guidance;
import com.japanese.lessons.repositories.IGuidanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GuidanceService {

    @Autowired private IGuidanceRepository iGuidanceRepository;
    @Autowired private DynamicRowService dynamicRowService;
    @Autowired private VocabularyService vocabularyService;

    private Guidance getGuidanceById(Long id) {
        Guidance guidance = iGuidanceRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Guidance not found by id: " + id)
        );
        System.out.println("Guidance found: " + guidance.getId());
        return guidance;
    }

    public ExplanationWithTableDTO getExplanationWithTables(Long guidanceId) {
        Guidance guidance = getGuidanceById(guidanceId);
        List<DynamicRow> dynamicRowList = dynamicRowService.getAllDynamicRowByGuidanceId(guidanceId);
        List<TableDTO> tableDTOList = formListOfTables(dynamicRowList);
        return new ExplanationWithTableDTO(guidance, tableDTOList);
    }

    private List<TableDTO> formListOfTables(List<DynamicRow> dynamicRowList) {
        List<TableDTO> tableDTOList = new ArrayList<>();
        for (DynamicRow dynamicRow : dynamicRowList) {
            List<Vocabulary> vocabularyList = vocabularyService.getAllVocabularyByIdsList(dynamicRow.getIds());
            tableDTOList.add(new TableDTO(dynamicRow, vocabularyList));
        }
        return tableDTOList;
    }
}
