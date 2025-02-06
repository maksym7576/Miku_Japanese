package com.japanese.lessons.repositories;

import com.japanese.lessons.models.Subtitle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ISubtitleRepository extends JpaRepository<Subtitle, Long> {
    List<Subtitle> findByVideoId(Long videoId);
}
