package com.japanese.lessons.repositories;

import com.japanese.lessons.models.fourth.Phrase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPhraseRepository extends JpaRepository<Phrase, Long> {
}
