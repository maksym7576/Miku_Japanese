package com.japanese.lessons.service;

import com.japanese.lessons.dtos.guidance.TableDTO;
import com.japanese.lessons.dtos.guidance.ExplanationWithTableDTO;
import com.japanese.lessons.dtos.response.models.GuidanceDTO;
import com.japanese.lessons.dtos.response.models.TextDTO;
import com.japanese.lessons.models.fifth.DynamicRow;
import com.japanese.lessons.models.fourth.Guidance;
import com.japanese.lessons.repositories.IGuidanceRepository;
import com.japanese.lessons.service.Lesson.MangaDialogueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GuidanceService {

    private static final Logger logger = LoggerFactory.getLogger(ExerciseService.class);
    @Autowired private IGuidanceRepository iGuidanceRepository;
    @Autowired private DynamicRowService dynamicRowService;
    @Autowired private MangaDialogueService mangaDialogueService;

    private Guidance getGuidanceById(Long id) {
        return iGuidanceRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Guidance not found by id: " + id)
        );
    }

    private GuidanceDTO formGuidanceGTO(Guidance guidance) {
        return new GuidanceDTO(guidance.getId(), guidance.getTopic(), guidance.getDescription());
    }

    public ExplanationWithTableDTO getExplanationWithTable(Long guidanceId) {
        Guidance guidance = getGuidanceById(guidanceId);
        logger.debug("Ids from guidance: {}", guidance.getIds());
        List<DynamicRow> dynamicRowList = dynamicRowService.getAllDynamicRowByGuidanceId(guidance.getIds());
        for (DynamicRow dynamicRow : dynamicRowList) {
            logger.debug("Ids from dynamic row: {}", dynamicRow.getIds());
        }
        List<TableDTO> tableDTOList = formPhrasesTableList(dynamicRowList);
        return new ExplanationWithTableDTO(formGuidanceGTO(guidance), tableDTOList);
    }

    private List<TableDTO> formPhrasesTableList(List<DynamicRow> dynamicRowList) {
        List<TableDTO> tableDTOList = new ArrayList<>();
        for (DynamicRow dynamicRow : dynamicRowList) {
            List<TextDTO> textList = mangaDialogueService.getTextDTOListByIdsListWithUnitingWords(dynamicRow.getIds());
            tableDTOList.add(new TableDTO(dynamicRowService.formDynamicRowDTO(dynamicRow), dynamicRow.getRowsType().toString(), textList));
        }
        return tableDTOList;
    }
}
