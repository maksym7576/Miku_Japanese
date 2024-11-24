package com.japanese.lessons.repositories;

import com.japanese.lessons.models.ETargetType;
import com.japanese.lessons.models.Vocabulary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IVocabularyRepository extends JpaRepository<Vocabulary, Long> {
    List<Vocabulary> findAllById(Iterable<Long> ids);
}
