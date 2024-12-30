package com.japanese.lessons.repositories;

import com.japanese.lessons.models.LevelGuidance;
import com.japanese.lessons.models.first.ELessonLevels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ILevelGuidanceRepository extends JpaRepository<LevelGuidance, Long> {
    @Query("SELECT lg FROM LevelGuidance lg WHERE lg.eLessonLevels = :eLessonLevels")
    List<LevelGuidance> findByELessonLevels(@Param("eLessonLevels") ELessonLevels eLessonLevels);
}
