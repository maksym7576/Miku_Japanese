package com.japanese.lessons.controller;

import com.japanese.lessons.dtos.CharacterPageDTO;
import com.japanese.lessons.dtos.LevelDTO;
import com.japanese.lessons.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/character")
public class CharacterController {

    @Autowired private CharacterService characterService;

    @GetMapping("/get/character/{characterName}/user/{userId}")
    public ResponseEntity<?> getAllWithUserId(@PathVariable String characterName,@PathVariable Long userId) {
        try {
            CharacterPageDTO characterPageDTO = characterService.formPageWitchCharacter(characterName, userId);
            return ResponseEntity.status(HttpStatus.OK).body(characterPageDTO);
        } catch (IllegalAccessError e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
