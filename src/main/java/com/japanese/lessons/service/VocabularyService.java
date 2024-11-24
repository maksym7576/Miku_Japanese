package com.japanese.lessons.service;

import com.japanese.lessons.models.Vocabulary;
import com.japanese.lessons.repositories.IVocabularyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VocabularyService {

    @Autowired private IVocabularyRepository iVocabularyRepository;

    public List<Vocabulary> getAllVocabularyByIdsList(List<Long> ids) {
        return iVocabularyRepository.findAllById(ids);
    }
}
