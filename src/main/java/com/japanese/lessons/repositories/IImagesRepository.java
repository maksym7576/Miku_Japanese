package com.japanese.lessons.repositories;

import com.japanese.lessons.models.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IImagesRepository extends JpaRepository<Images, Long> {
    List<Images> findByMangaId(Long imageId);
}
