package com.japanese.lessons.service;

import com.japanese.lessons.models.DynamicRow;
import com.japanese.lessons.models.EGuidanceType;
import com.japanese.lessons.models.ETargetType;
import com.japanese.lessons.repositories.IDynamicRowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DynamicRowService {

    @Autowired private IDynamicRowRepository iDynamicRowRepository;

    public List<DynamicRow> getAllDynamicRowByGuidanceId(Long guidanceId) {
        return iDynamicRowRepository.findByGuidanceId(guidanceId);
    }
}
