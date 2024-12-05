package com.japanese.lessons.repositories;

import com.japanese.lessons.models.sixsth.FileRecords;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IFileRecordRepository extends JpaRepository<FileRecords, Long> {
    List<FileRecords> findAllById(Iterable<Long> ids);
}
