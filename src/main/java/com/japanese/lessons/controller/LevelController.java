package com.japanese.lessons.controller;

import com.japanese.lessons.dtos.LevelDTO;
import com.japanese.lessons.models.Level;
import com.japanese.lessons.service.LevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/level")
public class LevelController {

    @Autowired private LevelService levelService;

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        try {
            List<Level> levelList = levelService.getAllLevelsAndSort();
            return ResponseEntity.status(HttpStatus.OK).body(levelList);
        } catch (IllegalAccessError e) {
          throw new IllegalArgumentException(e.getMessage());
        }
    }
    @GetMapping("/all/user/{userId}")
    public ResponseEntity<?> getAllWithUserId(@PathVariable Long userId) {
        try {
            List<LevelDTO> levelList = levelService.getAllLevelsAndFormAllLessonsWithUserId(userId);
            return ResponseEntity.status(HttpStatus.OK).body(levelList);
        } catch (IllegalAccessError e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
