package com.japanese.lessons.service;

import com.japanese.lessons.models.first.Character;
import com.japanese.lessons.models.second.Exercise;
import com.japanese.lessons.repositories.ICharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterService {

    @Autowired private ICharacterRepository iCharacterRepository;
    @Autowired private ExerciseService exerciseService;

    private List<com.japanese.lessons.models.first.Character> getAllCharacters() {
        return iCharacterRepository.findAll();
    }

    private com.japanese.lessons.models.first.Character filterCharacterByUsername(List<com.japanese.lessons.models.first.Character> characterList, String characterName) {
        return characterList.stream()
                .filter(character -> character.getName().equals(characterName))
                .findFirst()
                .orElse(null);
    }

    public void formPageWitchCharacter(String characterName) {
        List<com.japanese.lessons.models.first.Character> characterList = getAllCharacters();
        Character filteredCharacter = filterCharacterByUsername(characterList, characterName);
        List<Exercise> exerciseList = filteredCharacter.getExercises();

    }
}
