package com.japanese.lessons.repositories.Lesson;

import com.japanese.lessons.models.ETargetType;
import com.japanese.lessons.models.sixsth.Text;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IMangaTextRepository extends JpaRepository<Text, Long> {
    List<Text> findAllById(Iterable<Long> ids);
}
