package com.japanese.lessons.controller;

import com.japanese.lessons.models.sixsth.FileRecords;
import com.japanese.lessons.service.FFmpegFrameGrabberService;
import com.japanese.lessons.service.FileRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/extraction")
public class AudioExtractionController {

    @Autowired private FFmpegFrameGrabberService fFmpegFrameGrabberService;
    @Autowired private FileRecordService fileRecordService;

    @PostMapping("/audio/{videoId}")
    public ResponseEntity<byte[]> extractAudio(@PathVariable("videoId") Long videoId) {
        FileRecords fileRecords = fileRecordService.getFileRecordsById(videoId);
     return fFmpegFrameGrabberService.extractAudioFromVideo(fileRecords.getUrl());
    }
}
