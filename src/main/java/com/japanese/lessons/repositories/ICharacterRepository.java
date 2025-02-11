package com.japanese.lessons.repositories;

import com.japanese.lessons.models.first.Character;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICharacterRepository extends JpaRepository<Character, Long> {
}
