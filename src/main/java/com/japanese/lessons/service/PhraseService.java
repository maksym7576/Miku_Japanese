package com.japanese.lessons.service;

import com.japanese.lessons.dtos.ObjectWithMediaDTO;
import com.japanese.lessons.dtos.PhraseDTOWithSentence;
import com.japanese.lessons.dtos.response.models.FileRecordsDTO;
import com.japanese.lessons.dtos.response.models.PhraseDTO;
import com.japanese.lessons.dtos.response.models.TextDTO;
import com.japanese.lessons.models.fourth.Phrase;
import com.japanese.lessons.repositories.IPhraseRepository;
import com.japanese.lessons.service.Lesson.MangaDialogueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhraseService {

    @Autowired private IPhraseRepository iPhraseRepository;
    @Autowired private MangaDialogueService mangaDialogueService;
    @Autowired private FileRecordService fileRecordService;

    public Phrase getPhraseById(Long id) {
        return iPhraseRepository.findById(id).orElseThrow(() -> new RuntimeException("Phrase not found"));
    }

    private PhraseDTO generatePhrase(Phrase phrase) {
        return new PhraseDTO(phrase.getId(), phrase.getEMediaType());
    }

    public ObjectWithMediaDTO getPhraseWithMedia(Long phraseId) {
        Phrase phrase = getPhraseById(phraseId);
        List<FileRecordsDTO> fileRecordsDTOList = fileRecordService.createListFileRecordsDTOByIdsList(phrase.getIds());
        TextDTO textDTO = mangaDialogueService.getTextDTOByIdWithUnitingWords(phrase.getTextId());
        PhraseDTOWithSentence phraseDTOWithSentence = new PhraseDTOWithSentence(generatePhrase(phrase), textDTO);
        return new ObjectWithMediaDTO(phraseDTOWithSentence, fileRecordsDTOList);
    }
}
