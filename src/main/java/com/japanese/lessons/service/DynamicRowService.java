package com.japanese.lessons.service;

import com.japanese.lessons.dtos.response.models.DynamicRowDTO;
import com.japanese.lessons.models.fifth.DynamicRow;
import com.japanese.lessons.repositories.IDynamicRowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DynamicRowService {

    @Autowired private IDynamicRowRepository iDynamicRowRepository;

    public List<DynamicRow> getAllDynamicRowByGuidanceId(Long guidanceId) {
        return iDynamicRowRepository.findByGuidanceId(guidanceId);
    }

    public DynamicRowDTO formDynamicRowDTO(DynamicRow dynamicRow) {
        return new DynamicRowDTO(dynamicRow.getId(), dynamicRow.getTableName());
    }
}
