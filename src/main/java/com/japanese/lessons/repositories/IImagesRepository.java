package com.japanese.lessons.repositories;

import com.japanese.lessons.models.Images;
import org.springframework.data.repository.CrudRepository;

public interface IImagesRepository extends CrudRepository<Images, Long> {
}
