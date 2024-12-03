package com.japanese.lessons.service;

import com.japanese.lessons.models.FileRecords;
import com.japanese.lessons.repositories.IFileRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileRecordService {

    @Autowired private IFileRecordRepository iFileRecordRepository;

    public FileRecords saveFileRecords(FileRecords fileRecords) {
        return iFileRecordRepository.save(fileRecords);
    }
    public FileRecords getFileRecordsById(Long id) {
        return iFileRecordRepository.getReferenceById(id);
    }
}
