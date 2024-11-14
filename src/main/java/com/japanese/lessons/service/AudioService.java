package com.japanese.lessons.service;

import com.japanese.lessons.models.Audio;
import com.japanese.lessons.models.ETargetType;
import com.japanese.lessons.repositories.IAudioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AudioService {

    @Autowired
    private IAudioRepository iAudioRepository;

    public Audio getByTypeAndObjectId(ETargetType eTargetType, Long objectId) {
        return iAudioRepository.findByTargetTypeAndTargetId(eTargetType, objectId).orElse(null);
    }
}
