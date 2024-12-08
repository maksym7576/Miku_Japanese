package com.japanese.lessons.service;

import com.japanese.lessons.dtos.guidance.TableDTO;
import com.japanese.lessons.dtos.guidance.ExplanationWithTableDTO;
import com.japanese.lessons.dtos.response.models.TextDTO;
import com.japanese.lessons.models.fifth.DynamicRow;
import com.japanese.lessons.models.fourth.Guidance;
import com.japanese.lessons.repositories.IGuidanceRepository;
import com.japanese.lessons.service.Lesson.MangaDialogueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GuidanceService {

    @Autowired private IGuidanceRepository iGuidanceRepository;
    @Autowired private DynamicRowService dynamicRowService;
    @Autowired private MangaDialogueService mangaDialogueService;

    private Guidance getGuidanceById(Long id) {
        Guidance guidance = iGuidanceRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Guidance not found by id: " + id)
        );
        System.out.println("Guidance found: " + guidance.getId());
        return guidance;
    }

    public ExplanationWithTableDTO getExplanationWithTable(Long guidanceId) {
        Guidance guidance = getGuidanceById(guidanceId);
        List<DynamicRow> dynamicRowList = dynamicRowService.getAllDynamicRowByGuidanceId(guidanceId);
        List<TableDTO> tableDTOList = formPhrasesTableList(dynamicRowList);
        return new ExplanationWithTableDTO(guidance, tableDTOList);
    }

    private List<TableDTO> formPhrasesTableList(List<DynamicRow> dynamicRowList) {
        List<TableDTO> tableDTOList = new ArrayList<>();
        for (DynamicRow dynamicRow : dynamicRowList) {
            List<TextDTO> textList = mangaDialogueService.getTextDTOListByIdsListWithUnitingWords(dynamicRow.getIds());
            tableDTOList.add(new TableDTO(dynamicRowService.formDynamicRowDTO(dynamicRow), textList));
        }
        return tableDTOList;
    }
}
