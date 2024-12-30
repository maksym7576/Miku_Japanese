package com.japanese.lessons.repositories;

import com.japanese.lessons.models.Level;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ILevelRepository extends JpaRepository<Level, Long> {
}
