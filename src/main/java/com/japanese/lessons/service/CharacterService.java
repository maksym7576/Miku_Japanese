package com.japanese.lessons.service;

import com.japanese.lessons.dtos.CharacterDTO;
import com.japanese.lessons.dtos.CharacterPageDTO;
import com.japanese.lessons.dtos.response.models.ExerciseDTO;
import com.japanese.lessons.models.User.User;
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
    @Autowired private UserService userService;

    private List<com.japanese.lessons.models.first.Character> getAllCharacters() {
        return iCharacterRepository.findAll();
    }

    private com.japanese.lessons.models.first.Character filterCharacterByUsername(List<com.japanese.lessons.models.first.Character> characterList, String characterName) {
        return characterList.stream()
                .filter(character -> character.getName().equals(characterName))
                .findFirst()
                .orElse(null);
    }

    private CharacterDTO formCharacterDTO(Character character, Integer characterLevel, Integer characterMood) {
        return new CharacterDTO(character.getName(), character.getHistory(), characterLevel, characterMood);
    }

    public CharacterPageDTO formPageWitchCharacter(String characterName, Long userId) {
        List<com.japanese.lessons.models.first.Character> characterList = getAllCharacters();
        Character filteredCharacter = filterCharacterByUsername(characterList, characterName);
        List<Exercise> exerciseList = filteredCharacter.getExercises();
        List<ExerciseDTO> novelsList = exerciseService.formNovelsListForCharacterByCharacterId(filteredCharacter.getId(), userId, exerciseList);
        User user = userService.getUserById(userId);
        CharacterDTO characterDTO = formCharacterDTO(filteredCharacter, user.getMikuLevel(), user.getMikuMood());
        return new CharacterPageDTO(characterDTO, novelsList);
    }
}
