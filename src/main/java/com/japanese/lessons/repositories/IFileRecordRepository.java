package com.japanese.lessons.repositories;

import com.japanese.lessons.models.FileRecords;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFileRecordRepository extends JpaRepository<FileRecords, Long> {
}
