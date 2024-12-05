package com.japanese.lessons.service;

import com.japanese.lessons.dtos.response.models.FileRecordsDTO;
import com.japanese.lessons.models.sixsth.FileRecords;
import com.japanese.lessons.repositories.IFileRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FileRecordService {

    @Autowired private IFileRecordRepository iFileRecordRepository;

    public FileRecords saveFileRecords(FileRecords fileRecords) {
        return iFileRecordRepository.save(fileRecords);
    }
    public FileRecords getFileRecordsById(Long id) {
        return iFileRecordRepository.getReferenceById(id);
    }
    private List<FileRecords> getFileRecordsListByIds(List<Long> ids) {
        return iFileRecordRepository.findAllById(ids);
    }

    private FileRecordsDTO formFileRecordsDTO(FileRecords fileRecords) {
        return new FileRecordsDTO(fileRecords.getId(), fileRecords.getUrl(), fileRecords.getFileURLType().toString());
    }

    private List<FileRecordsDTO> formListFileRecordsDTO(List<FileRecords> fileRecordsList) {
        List<FileRecordsDTO> fileRecordsDTOList = new ArrayList<>();
        for (FileRecords fileRecords : fileRecordsList) {
            fileRecordsDTOList.add(formFileRecordsDTO(fileRecords));
        }
        return fileRecordsDTOList;
    }

    public List<FileRecordsDTO> createListFileRecordsDTOByIdsList(List<Long> idsList) {
        List<FileRecords> fileRecordsList = getFileRecordsListByIds(idsList);
        return formListFileRecordsDTO(fileRecordsList);
    }
}
