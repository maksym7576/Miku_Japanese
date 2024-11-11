package com.japanese.lessons.repositories;

import com.japanese.lessons.models.Audio;
import com.japanese.lessons.models.ETargetType;
import com.japanese.lessons.models.lesson.mangaExercise.Text;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IAudioRepository extends JpaRepository<Audio, Long> {
    Optional<Audio> findByTargetTypeAndTargetId(ETargetType targetType, Long targetId);
}
